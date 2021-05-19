package com.cs492e.vocali.repository

import com.cs492e.vocali.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, Int>