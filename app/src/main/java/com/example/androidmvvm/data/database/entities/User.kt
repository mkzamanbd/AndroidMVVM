package com.example.androidmvvm.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0
@Entity
data class User (
    var id: Int? = null,
    var name: String? = null,
    var email: String? = null,
    var username: String? = null,
    var email_verified_at: String? = null,
    var current_team_id: String? = null,
    var profile_photo_path: String? = null,
    var github_id: String? = null,
    var facebook_id: String? = null,
    var google_id: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
    var profile_photo_url: String? = null,
    var user_created_at: String? = null,
){
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}