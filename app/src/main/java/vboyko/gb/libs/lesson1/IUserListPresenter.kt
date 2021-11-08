package vboyko.gb.libs.lesson1

interface IUserListPresenter : IListPresenter<UserItemView> {

    fun getUserByPosition(position: Int): GithubUser
}