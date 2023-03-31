package com.example.recycler_view_example

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recycler_view_example.databinding.ItemUserBinding
import com.example.recycler_view_example.models.User

interface UserActionListener {

    fun onUserDetails(user: User)

    fun onUserRemove(user: User)

    fun onUserMove(user: User, moveBy: Int)

    fun onUserFire(user: User)
}

class UserDiffCallback(
    private val oldList: List<User>,
    private val newList: List<User>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.id == newUser.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser == newUser
    }
}

class UsersAdapter(private val actionListener: UserActionListener) :
    RecyclerView.Adapter<UsersAdapter.UserHolder>(), View.OnClickListener {

    var users: List<User> = emptyList()
        set(newValue) {
            val diffUtil = UserDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffUtil)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.root.setOnClickListener(this)
        binding.moreImageView.setOnClickListener(this)

        return UserHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun onClick(v: View) {
        val user = v.tag as User
        when (v.id) {
            R.id.moreImageView -> showPopupMenu(v)
            else -> actionListener.onUserDetails(user)
        }
    }

    private fun showPopupMenu(view: View) {
        val context = view.context
        val popupMenu = PopupMenu(context, view)
        val user = view.tag as User
        val position = users.indexOfFirst { user.id == it.id }

        popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, context.getString(R.string.move_up)).apply {
            isEnabled = position > 0
        }
        popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, context.getString(R.string.move_down))
            .apply {
                isEnabled = position < users.size - 1
            }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove))
        popupMenu.menu.add(0, ID_FIRE, Menu.NONE, context.getString(R.string.fire))
            .apply {
                isEnabled = user.company.isNotBlank()
            }


        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_REMOVE -> actionListener.onUserRemove(user)
                ID_MOVE_UP -> actionListener.onUserMove(user, -1)
                ID_MOVE_DOWN -> actionListener.onUserMove(user, 1)
                ID_FIRE -> actionListener.onUserFire(user)
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    class UserHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            val context = itemView.context
            with(binding) {
                itemView.tag = user
                moreImageView.tag = user

                userNameTextView.text = user.name
                userCompanyTextView.text =
                    user.company.ifBlank { context.getString(R.string.unemployed) }
                if (user.photo.isNotBlank()) {
                    Glide.with(userPhotoImageView.context)
                        .load(user.photo)
                        .circleCrop()
                        .placeholder(R.drawable.ic_user)
                        .error(R.drawable.ic_user)
                        .into(userPhotoImageView)
                } else {
                    Glide.with(userPhotoImageView.context).clear(userPhotoImageView)
                    userPhotoImageView.setImageResource(R.drawable.ic_user)
                }
            }
        }
    }

    companion object {
        private const val ID_REMOVE = 1
        private const val ID_MOVE_UP = 2
        private const val ID_MOVE_DOWN = 3
        private const val ID_FIRE = 4
    }
}