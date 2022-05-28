package com.company.capstoneapp

import com.google.gson.annotations.SerializedName

data class DataFood(

	@field:SerializedName("found")
	val found: List<FoundItem>,

	@field:SerializedName("readTime")
	val readTime: String
)

data class Properties(

	@field:SerializedName("rate")
	val rate: Rate,

	@field:SerializedName("latitude")
	val latitude: Latitude,

	@field:SerializedName("name")
	val name: Name,

	@field:SerializedName("link")
	val link: Link,

	@field:SerializedName("description")
	val description: Description,

	@field:SerializedName("halal")
	val halal: Halal,

	@field:SerializedName("longitude")
	val longitude: Longitude
)

data class Rate(

	@field:SerializedName("doubleValue")
	val doubleValue: Double
)

data class Description(

	@field:SerializedName("stringValue")
	val stringValue: String,

	@field:SerializedName("meaning")
	val meaning: Int,

	@field:SerializedName("excludeFromIndexes")
	val excludeFromIndexes: Boolean
)

data class Entity(

	@field:SerializedName("key")
	val key: Key,

	@field:SerializedName("properties")
	val properties: Properties
)

data class PartitionId(

	@field:SerializedName("projectId")
	val projectId: String
)

data class Halal(

	@field:SerializedName("booleanValue")
	val booleanValue: Boolean
)

data class FoundItem(

	@field:SerializedName("updateTime")
	val updateTime: String,

	@field:SerializedName("version")
	val version: String,

	@field:SerializedName("entity")
	val entity: Entity
)

data class Link(

	@field:SerializedName("stringValue")
	val stringValue: String,

	@field:SerializedName("excludeFromIndexes")
	val excludeFromIndexes: Boolean
)

data class Name(

	@field:SerializedName("stringValue")
	val stringValue: String
)

data class Longitude(

	@field:SerializedName("doubleValue")
	val doubleValue: Double,

	@field:SerializedName("excludeFromIndexes")
	val excludeFromIndexes: Boolean
)

data class Key(

	@field:SerializedName("path")
	val path: List<PathItem>,

	@field:SerializedName("partitionId")
	val partitionId: PartitionId
)

data class Latitude(

	@field:SerializedName("doubleValue")
	val doubleValue: Double,

	@field:SerializedName("excludeFromIndexes")
	val excludeFromIndexes: Boolean
)

data class PathItem(

	@field:SerializedName("kind")
	val kind: String,

	@field:SerializedName("name")
	val name: String
)
