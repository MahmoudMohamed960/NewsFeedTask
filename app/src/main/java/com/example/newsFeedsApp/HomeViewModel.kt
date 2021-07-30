package com.example.newsFeedsApp

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    var navDrawerItems = LinkedHashMap<Int, Int>()

    fun getNavDrawerList(): LinkedHashMap<Int, Int> {
        navDrawerItems[R.string.explore_text] = R.drawable.ic_explore
        navDrawerItems[R.string.live_chat_text] = R.drawable.ic_live
        navDrawerItems[R.string.gallery] = R.drawable.ic_gallery
        navDrawerItems[R.string.wish_list] = R.drawable.ic_wishlist
        navDrawerItems[R.string.magazine_text] = R.drawable.ic_e_magazine
        return navDrawerItems
    }
}