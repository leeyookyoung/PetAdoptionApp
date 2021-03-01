package com.example.androiddevchallenge

import androidx.compose.runtime.Immutable


enum class Pet {
    Dog, Cat, Rabbit, Etc
}

@Immutable
data class ShelterPet(
    val id: Long,
    val type: Pet,
    val sex: Char,
    val breed: String,
    val regDate: String,
    val location: String,
    val rescuedLocation: String,
    val character: String,
    val thumbUrl: String
)

@Immutable
data class MissedPet(
    val id: Long,
    val type: Pet,
    val sex: Char,
    val breed: String,
    val regDate: String,
    val location: String,
    val missedLocation: String,
    val thumbUrl: String
)

object PetRepo {
    fun getMissingPet(id: Long): MissedPet = missingPets.find { it.id == id }!!
}

val shelterPets = listOf(
    ShelterPet(
        id = 0,
        type = Pet.Dog,
        sex = 'f',
        breed = "Akbash",
        regDate = "2021-02-21",
        location= "Korea",
        rescuedLocation = "Korea Seoul",
        character = "Cute and affectionate\nNot sensitive, but quiet\nEat well and sleep well.",
        thumbUrl = "https://ykladopt.imgix.net/01.jpg"
    ),
    ShelterPet(
        id = 1,
        type = Pet.Cat,
        sex = 'm',
        breed = "America Short Hair",
        regDate = "2021-03-01",
        location= "Korea",
        rescuedLocation = "Korea Seoul",
        character = "Curious and active\nEat well and sleep well.",
        thumbUrl = "https://ykladopt.imgix.net/02.jpg"
    ),
    ShelterPet(
        id = 2,
        type = Pet.Cat,
        sex = 'f',
        breed = "American Curl Cat",
        regDate = "2021-01-01",
        location= "Korea",
        rescuedLocation = "Korea Seoul",
        character = "Curious and active\nEat well and sleep well.",
        thumbUrl = "https://ykladopt.imgix.net/03.jpg"
    ),
    ShelterPet(
        id = 3,
        type = Pet.Dog,
        sex = 'm',
        breed = "Shepherd",
        regDate = "2021-02-01",
        location= "Korea",
        rescuedLocation = "Korea Seoul",
        character = "Cute and affectionate\nNot sensitive, but quiet\nEat well and sleep well.",
        thumbUrl = "https://ykladopt.imgix.net/04.jpg"
    ),
    ShelterPet(
        id = 4,
        type = Pet.Cat,
        sex = 'f',
        breed = "Bengal",
        regDate = "2020-12-21",
        location= "Korea",
        rescuedLocation = "Korea Seoul",
        character = "Curious and active\nEat well and sleep well.",
        thumbUrl = "https://ykladopt.imgix.net/05.jpg"
    ),
    ShelterPet(
        id = 5,
        type = Pet.Dog,
        sex = 'm',
        breed = "Black Mouth Cur",
        regDate = "2021-01-17",
        location= "Korea",
        rescuedLocation = "Korea Seoul",
        character = "Curious and active\nEat well and sleep well.",
        thumbUrl = "https://ykladopt.imgix.net/06.jpg"
    ),
    ShelterPet(
        id = 6,
        type = Pet.Dog,
        sex = 'f',
        breed = "Maltese",
        regDate = "2021-02-21",
        location= "Korea",
        rescuedLocation = "Korea Seoul",
        character = "Cute and affectionate\nNot sensitive, but quiet\nEat well and sleep well.",
        thumbUrl = "https://ykladopt.imgix.net/07.jpg"
    ),
    ShelterPet(
        id = 7,
        type = Pet.Dog,
        sex = 'm',
        breed = "Bullboxer Pit",
        regDate = "2020-11-01",
        location= "Korea",
        rescuedLocation = "Korea Seoul",
        character = "Curious and active\nEat well and sleep well.",
        thumbUrl = "https://ykladopt.imgix.net/08.jpg"
    ),
    ShelterPet(
        id = 8,
        type = Pet.Cat,
        sex = 'f',
        breed = "Ocicat Cat",
        regDate = "2021-02-21",
        location= "Korea",
        rescuedLocation = "Korea Seoul",
        character = "Curious and active\nEat well and sleep well.",
        thumbUrl = "https://ykladopt.imgix.net/09.jpg"
    ),
    ShelterPet(
        id = 9,
        type = Pet.Rabbit,
        sex = 'm',
        breed = "Korea Rabbit",
        regDate = "2021-01-17",
        location= "Korea",
        rescuedLocation = "Korea Seoul",
        character = "Cute and affectionate\nNot sensitive, but quiet\nEat well and sleep well.",
        thumbUrl = "https://ykladopt.imgix.net/10.jpg"
    ),
    ShelterPet(
        id = 10,
        type = Pet.Cat,
        sex = 'f',
        breed = "Turkish Van",
        regDate = "2021-02-21",
        location= "Korea",
        rescuedLocation = "Korea Seoul",
        character = "Curious and active\nEat well and sleep well.",
        thumbUrl = "https://ykladopt.imgix.net/11.jpg"
    ),
    ShelterPet(
        id = 11,
        type = Pet.Cat,
        sex = 'm',
        breed = "Turkish Van",
        regDate = "2021-03-01",
        location= "Korea",
        rescuedLocation = "Korea Seoul",
        character = "Cute and affectionate\nNot sensitive, but quiet\nEat well and sleep well.",
        thumbUrl = "https://ykladopt.imgix.net/12.jpg"
    )
)

val missingPets = listOf(
    MissedPet(
        id = 0,
        type = Pet.Cat,
        sex = 'f',
        breed = "Korea Short Hair",
        regDate = "2021-02-21",
        location= "Korea",
        missedLocation = "Korea Seoul",
        thumbUrl = "https://ykladopt.imgix.net/13.jpg"
    ),
    MissedPet(
        id = 1,
        type = Pet.Cat,
        sex = 'm',
        breed = "Norwegian Forest",
        regDate = "2021-03-01",
        location= "Korea",
        missedLocation = "Korea Seoul",
        thumbUrl = "https://ykladopt.imgix.net/14.jpg"
    ),
    MissedPet(
        id = 2,
        type = Pet.Cat,
        sex = 'f',
        breed = "Korea Short Hair",
        regDate = "2021-02-21",
        location= "Korea",
        missedLocation = "Korea Seoul",
        thumbUrl = "https://ykladopt.imgix.net/15.jpg"
    ),
    MissedPet(
        id = 3,
        type = Pet.Dog,
        sex = 'm',
        breed = "Catahoula Leopard",
        regDate = "2021-03-01",
        location= "Korea",
        missedLocation = "Korea Seoul",
        thumbUrl = "https://ykladopt.imgix.net/16.jpg"
    ),
    MissedPet(
        id = 4,
        type = Pet.Dog,
        sex = 'f',
        breed = "Golden Retriever",
        regDate = "2021-02-21",
        location= "Korea",
        missedLocation = "Korea Seoul",
        thumbUrl = "https://ykladopt.imgix.net/17.jpg"
    ),
    MissedPet(
        id = 5,
        type = Pet.Cat,
        sex = 'm',
        breed = "Korea Short Hair",
        regDate = "2021-03-01",
        location= "Korea",
        missedLocation = "Korea Seoul",
        thumbUrl = "https://ykladopt.imgix.net/18.jpg"
    ),
    MissedPet(
        id = 6,
        type = Pet.Cat,
        sex = 'f',
        breed = "Korea Short Hair",
        regDate = "2021-02-21",
        location= "Korea",
        missedLocation = "Korea Seoul",
        thumbUrl = "https://ykladopt.imgix.net/19.jpg"
    ),
    MissedPet(
        id = 7,
        type = Pet.Dog,
        sex = 'm',
        breed = "Border Collie",
        regDate = "2021-03-01",
        location= "Korea",
        missedLocation = "Korea Seoul",
        thumbUrl = "https://ykladopt.imgix.net/20.jpg"
    )
)