package vboyko.gb.libs.lesson1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import vboyko.gb.libs.lesson1.databinding.FragmentUsersBinding
import javax.inject.Inject

class UsersFragment : MvpAppCompatFragment(), UsersView, BackButtonListener {
    companion object {
        fun newInstance() = UsersFragment().apply {
            App.instance.appComponent.inject(this)
        }
    }

    @Inject
    lateinit var somePresenter: UsersPresenter

    val presenter: UsersPresenter by moxyPresenter {
        somePresenter
    }

    var adapter: UsersRVAdapter? = null
    private var vb: FragmentUsersBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentUsersBinding.inflate(inflater, container, false).also {
            vb = it
        }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun init() {
        vb?.run {
            this.rvUsers.layoutManager = LinearLayoutManager(context)
            adapter = UsersRVAdapter(presenter.usersListPresenter)
            this.rvUsers.adapter = adapter
        }

        vb?.image?.run {
            Glide.with(this)
                .load("https://news102.ru/wp-content/uploads/2020/07/july.png")
                .placeholder(R.drawable.ic_launcher_foreground_2)
                .into(this)
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}

