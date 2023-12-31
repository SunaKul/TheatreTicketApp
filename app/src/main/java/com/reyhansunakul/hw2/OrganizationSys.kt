package com.reyhansunakul.hw2

object OrganizationSys {
    private val organizationArrayList: ArrayList<Organization> = ArrayList()

    fun prepareData() {
        organizationArrayList.clear()
        organizationArrayList.addAll(
            Movie("Pixar", "Incredible Family", 1000),
            Theatre("Ankara State Theatre", "D", "Suna Kul"),
            Theatre("Bilkent Theatre", "Comedy", "Enes Cinar"),
            Movie("Marvel", "Horror", 250),
            Movie("Mikro Production", "Family", 150)
        )
    }

    fun getOrganizationArrayList(): ArrayList<Organization> {
        return organizationArrayList
    }
}

private fun <E : Organization> java.util.ArrayList<E>.addAll(
    movie: E,
    theatre: E,
    theatre1: E,
    movie1: E,
    movie2: E
) {
    add(movie)
    add(theatre)
    add(theatre1)
    add(movie1)
    add(movie2)
}

