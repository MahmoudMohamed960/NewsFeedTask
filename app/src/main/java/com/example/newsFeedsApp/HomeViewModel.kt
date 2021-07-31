package com.example.newsFeedsApp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val api: ArticleApi) : ViewModel() {
    private var navDrawerItems = LinkedHashMap<Int, Int>()
    private val API_KEY = "533af958594143758318137469b41ba9"
    private val _articlesList: MutableLiveData<Resource<ArticleResponse>> = MutableLiveData()
    val articlesList: LiveData<Resource<ArticleResponse>>
        get() = _articlesList

    var resource: Resource<ArticleResponse>? = null

    fun getNavDrawerList(): LinkedHashMap<Int, Int> {
        navDrawerItems[R.string.explore_text] = R.drawable.ic_explore
        navDrawerItems[R.string.live_chat_text] = R.drawable.ic_live
        navDrawerItems[R.string.gallery] = R.drawable.ic_gallery
        navDrawerItems[R.string.wish_list] = R.drawable.ic_wishlist
        navDrawerItems[R.string.magazine_text] = R.drawable.ic_e_magazine
        return navDrawerItems
    }

    fun getArticlesList() {
        resource =
            Resource(Status.LOADING, null, null, null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val resOne = async { api.getArticlesList("the-next-web", API_KEY) }
                val resTwo = async { api.getArticlesList("associated-press", API_KEY) }
                if (resOne.await().isSuccessful && resTwo.await().isSuccessful) {
                    resOne.await().body()?.let {
                        resTwo.await().body()?.let { it1 ->
                            onResponse(it, it1)
                        }
                    }
                } else {
                    resource =
                        Resource(Status.ERROR, null, null, resOne.await().errorBody().toString())
                }
            } catch (exception: Exception) {
                resource = Resource(Status.ERROR, null, null, exception.message)
                _articlesList.postValue(resource)
            }
        }
    }

    private fun onResponse(resOne: Any, resTwo: Any) {
        var nextWebResponse = resOne as ArticleResponse
        var associatedPressResponse = resTwo as ArticleResponse

//        if (nextWebResponse.status == "ok" && associatedPressResponse.status == "ok") {
//            var articlesList = nextWebResponse.articles
//            associatedPressResponse.articles?.let { articlesList?.addAll(it) }
        resource = Resource(Status.SUCCESS, nextWebResponse, associatedPressResponse, null)
        _articlesList.postValue(resource)
    }
}