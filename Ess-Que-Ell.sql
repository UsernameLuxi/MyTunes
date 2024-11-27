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

CREATE TABLE Songs(
    Id INT NOT NULL IDENTITY (1,1) PRIMARY KEY,
    Title NVARCHAR(500) NOT NULL,
    ArtistID INT NOT NULL fk,

    URL NVARCHAR(1500) NOT NULL
);

CREATE TABLE Artist(
    Id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    ArtistName NVARCHAR(500) NOT NULL
);

CREATE TABLE Playlists(
    Id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    PlaylistName NVARCHAR(255) NOT NULL
);

CREATE TABLE Genre(

);

CREATE TABLE PlaylistContainer(

);