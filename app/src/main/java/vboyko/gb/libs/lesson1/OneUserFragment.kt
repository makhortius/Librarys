package vboyko.gb.libs.lesson1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import vboyko.gb.libs.lesson1.databinding.FragmentOneUserBinding
import javax.inject.Inject

class OneUserFragment : MvpAppCompatFragment(), OneUserView, BackButtonListener {
    companion object {

        private const val USER_NAME = "userName"
        private const val USER_AVATAR_URL = "userAvatarUrl"

        fun newInstance(userName: String, userAvatarUrl: String) = OneUserFragment()
            .apply {
                arguments = Bundle().apply {
                    this.putString(USER_NAME, userName)
                    this.putString(USER_AVATAR_URL, userAvatarUrl)
                }
                App.instance.appComponent.inject(this)
            }
    }

    @Inject
    lateinit var somePresenter: OneUserPresenter

    private val presenter: OneUserPresenter by moxyPresenter {
        somePresenter
    }

    private var vb: FragmentOneUserBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout {
        this.arguments?.run {
            presenter.updateUserInfo(
                this.getString(USER_NAME).orEmpty(),
                this.getString(USER_AVATAR_URL).orEmpty()
            )
        }
        return FragmentOneUserBinding.inflate(inflater, container, false).also {
            vb = it
        }.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun init() {
    }

    override fun updateUserData(userName: String, userAvatarUrl: String) {
        vb?.apply {
            Glide.with(this.image)
                .load(userAvatarUrl)
                .placeholder(R.drawable.ic_launcher_foreground_2)
                .into(this.image)
            this.userName.text = userName
        }

    }

    override fun backPressed() = presenter.backPressed()
}

