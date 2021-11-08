package vboyko.gb.libs.lesson1

interface IListPresenter<V : IItemView> {
    var itemClickListener: ((V, GithubUser) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}