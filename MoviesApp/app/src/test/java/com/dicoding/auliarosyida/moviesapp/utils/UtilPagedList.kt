package com.dicoding.auliarosyida.moviesapp.utils

import androidx.paging.PagedList
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

object UtilPagedList {

    fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = mock(PagedList::class.java) as PagedList<T>
        `when`(pagedList[anyInt()]).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        `when`(pagedList.size).thenReturn(list.size)

        return pagedList
    }
}