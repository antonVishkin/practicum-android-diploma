package ru.practicum.android.diploma.ui.favorite

import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.Salary

val vacancie1 = Vacancy(
        id = "1",
        name = "Software Engineer",
        salary = Salary("USD", 5000, 7000),
        city = "New York",
        employerName = "Tech Corp",
        urlImage = "https://example.com/image1.png"
    )
val vacancie2 =Vacancy(
        id = "2",
        name = "Data Scientist",
        salary = Salary("USD", 6000, 8000),
        city = "San Francisco",
        employerName = "Data Inc",
        urlImage = "https://example.com/image2.png"
    )
