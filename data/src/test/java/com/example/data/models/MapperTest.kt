package com.example.data.models

import com.example.data.models.details.AuthorDetailsItem
import com.example.data.models.list.AuthorItem
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class DataModelsTest {

    @Test
    fun `toAuthorItemEntity should map AuthorItem to AuthorItemEntity correctly`() {
        // Arrange
        val authorItem = AuthorItem(
            numFound = 1L,
            start = 0L,
            numFoundExact = true,
            docs = listOf(
                mockk(relaxed = true){
                    every { key } returns "OL1A"
                    every { name } returns "Author Name"
                    every { topSubjects } returns listOf("Subject 1", "Subject 2")
                }
            )
        )

        // Act
        val authorItemEntity = authorItem.toAuthorItemEntity()

        // Assert
        assertEquals(1, authorItemEntity.size)
        val entity = authorItemEntity[0]
        assertEquals("OL1A", entity.authorId)
        assertEquals("Author Name", entity.name)
        assertEquals("Top subjects: Subject 1, Subject 2", entity.topSubjects)
        assertEquals("https://covers.openlibrary.org/a/olid/OL1A.jpg", entity.profileImage)
    }

    @Test
    fun `toAuthorItemEntity should handle null topSubjects`() {
        // Arrange
        val authorItem = AuthorItem(
            numFound = 1L,
            start = 0L,
            numFoundExact = true,
            docs = listOf(
                mockk(relaxed = true){
                    every { key } returns "OL1A"
                    every { name } returns "Author Name 2"
                    every { topSubjects } returns null
                }
            )
        )

        // Act
        val authorItemEntity = authorItem.toAuthorItemEntity()

        // Assert
        assertEquals(1, authorItemEntity.size)
        val entity = authorItemEntity[0]
        assertEquals("OL1A", entity.authorId)
        assertEquals("Author Name 2", entity.name)
        assertEquals("", entity.topSubjects) // Empty string for null topSubjects
    }

    @Test
    fun `toAuthorDetailsItemEntity should map AuthorDetailsItem to AuthorDetailsItemEntity correctly`() {
        // Arrange
        val authorDetailsItem = mockk<AuthorDetailsItem>(){
            every { key } returns "OL3A"
            every { name } returns "Author Name 3"
            every { bio } returns "Author Bio"
        }
        val authorId = "OL3A"

        // Act
        val authorDetailsItemEntity = authorDetailsItem.toAuthorDetailsItemEntity(authorId)

        // Assert
        assertEquals("OL3A", authorDetailsItemEntity.authorId)
        assertEquals("Author Name 3", authorDetailsItemEntity.name)
        assertEquals("Author Bio", authorDetailsItemEntity.description)
        assertEquals("https://covers.openlibrary.org/a/olid/OL3A.jpg", authorDetailsItemEntity.profileImage)
    }
}