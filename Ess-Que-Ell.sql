-- SQL file containing the structure of the database. This file is for recreating the database only
USE master;
GO
IF EXISTS(SELECT * FROM sys.databases WHERE name = 'DB_NAME')
BEGIN
  DROP DATABASE DB_NAME;
END

CREATE DATABASE DB_NAME;
GO
USE DB_NAME;
GO

CREATE TABLE Artist(
    Id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    ArtistName NVARCHAR(500) NOT NULL UNIQUE
);

CREATE TABLE Genre(
    Id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    GenreName NVARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE Playlists(
    Id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    PlaylistName NVARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE Songs(
    Id INT NOT NULL IDENTITY (1,1) PRIMARY KEY,
    Title NVARCHAR(500) NOT NULL,
    ArtistID INT NOT NULL FOREIGN KEY REFERENCES Artist(Id) ON DELETE CASCADE,
    Duration TIME NOT NULL,
    GenreID INT NOT NULL FOREIGN KEY REFERENCES Genre(Id),
    URL NVARCHAR(1500) NOT NULL
);

CREATE TABLE PlaylistContainer(
    PlaylistID INT NOT NULL FOREIGN KEY REFERENCES Playlists(Id) ON DELETE CASCADE,
    SongID INT NOT NULL FOREIGN KEY REFERENCES Songs(Id) ON DELETE CASCADE,
    SongIndex INT NOT NULL
);