package io.arkansasio.hp3000xl.apps.pim

/** Canonical Contact model lives in PimModels.kt. */
class ContactsModel {
    private val contacts = mutableListOf<Contact>()
    fun add(contact: Contact) { contacts.removeAll { it.id == contact.id }; contacts += contact }
    fun remove(id: Long) { contacts.removeAll { it.id == id } }
    fun list(): List<Contact> = contacts.toList()
}
