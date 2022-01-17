package com.bettingstrategies.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bettingstrategies.R

interface InitViews {

    fun initViews()

    fun observeViewModel()

}

interface NavigationHost{

    fun setupToolbar(toolbar: Toolbar)

}

abstract class BaseFragment: Fragment(), InitViews {

    private var navigationHost: NavigationHost? = null
    private var itemView: View? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationHost) navigationHost = context
    }

    override fun onDetach() {
        super.onDetach()
        navigationHost = null
        itemView = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemView = view
    }

    override fun onResume() {
        super.onResume()

        val host = navigationHost ?: return
        val mainToolbar: Toolbar = itemView?.findViewById(R.id.toolbar) ?: return

        host.setupToolbar(mainToolbar)
    }

}