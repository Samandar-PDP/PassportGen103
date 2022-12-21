package uz.digital.passportgeneration.database

import uz.digital.passportgeneration.model.Passport

interface PassportService {
    fun savePassport(passport: Passport)
    fun updatePassport(passport: Passport)
    fun deletePassport(id: Int)
    fun getPassports(): List<Passport>
}