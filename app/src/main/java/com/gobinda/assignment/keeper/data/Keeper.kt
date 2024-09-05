package com.gobinda.assignment.keeper.data

import com.google.gson.annotations.SerializedName


data class Section(
    var sectionType: String? = null,
    var items: List<Items> = arrayListOf()
)