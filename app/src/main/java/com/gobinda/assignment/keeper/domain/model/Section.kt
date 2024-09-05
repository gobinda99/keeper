package com.gobinda.assignment.keeper.domain.model

import com.google.gson.annotations.SerializedName


data class Section(
    @SerializedName("sectionType")
    var sectionType: SectionType? = null,
    @SerializedName("items")
    var products: List<Product> = arrayListOf()
)