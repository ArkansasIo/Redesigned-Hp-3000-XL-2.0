package io.arkansasio.hp3000xl.apps.pim

data class Contact(val id: Long, val name: String, val phone: String = "", val email: String = "")

class ContactsModel {
    private val contacts = mutableListOf<Contact>()
    fun add(contact: Contact) { contacts.removeAll { it.id == contact.id }; contacts += contact }
    fun remove(id: Long) { contacts.removeAll { it.id == id } }
    fun list(): List<Contact> = contacts.toList()
}
