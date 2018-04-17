-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: mysql.stud.ntnu.no
-- Generation Time: 17. Apr, 2018 15:39 PM
-- Server-versjon: 5.7.21-0ubuntu0.16.04.1
-- PHP Version: 7.0.25-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `henrhoi_TDT4145`
--

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `exercise`
--

CREATE TABLE `exercise` (
  `navn` varchar(30) NOT NULL,
  `beskrivelse` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `exercise`
--

INSERT INTO `exercise` (`navn`, `beskrivelse`) VALUES
('Benkpress', ''),
('Biceps Curl', ''),
('Dips', ''),
('Hang ups', ' dradeg opp'),
('intervall', ''),
('intevall', ''),
('Knebøy', ''),
('Markløft', ''),
('Nedtrekk', ''),
('Pull ups', ''),
('pushups', ''),
('Skråbenk', ' benkpress med vinkle'),
('Tango', 'DANSAAS'),
('tåhev', ' løft tærne');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `exercisegroup`
--

CREATE TABLE `exercisegroup` (
  `navn` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `exercisegroup`
--

INSERT INTO `exercisegroup` (`navn`) VALUES
('Armer'),
('Ben'),
('Bryst'),
('fdsfd'),
('fdsfd553'),
('fingre'),
('løpe'),
('Rygg'),
('TestGroup'),
('TESTGRUPPE'),
('TESTGRUPPE2'),
('TESTGRUPPE3'),
('TESTGRUPPE33'),
('tåer'),
('tær');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `exerciseonmachine`
--

CREATE TABLE `exerciseonmachine` (
  `exercisename` varchar(30) NOT NULL,
  `machinename` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `exerciseonmachine`
--

INSERT INTO `exerciseonmachine` (`exercisename`, `machinename`) VALUES
('Tango', 'mølle'),
('Benkpress', 'SexMachine');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `groupcontainsexersice`
--

CREATE TABLE `groupcontainsexersice` (
  `gruppenavn` varchar(30) NOT NULL,
  `exersicename` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `groupcontainsexersice`
--

INSERT INTO `groupcontainsexersice` (`gruppenavn`, `exersicename`) VALUES
('Bryst', 'Benkpress'),
('Armer', 'Biceps Curl'),
('Armer', 'Dips'),
('Ben', 'Knebøy'),
('Ben', 'Markløft'),
('Rygg', 'Nedtrekk'),
('Rygg', 'Pull ups'),
('Armer', 'Tango');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `machine`
--

CREATE TABLE `machine` (
  `navn` varchar(30) NOT NULL,
  `beskrivelse` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `machine`
--

INSERT INTO `machine` (`navn`, `beskrivelse`) VALUES
('3dmølle', 'testerud'),
('løpemaskin', ' løp på den'),
('mølle', ' denne løper man på'),
('SexMachine', 'Traning on it'),
('TestMachine', 'Testing');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `workout`
--

CREATE TABLE `workout` (
  `dato` date NOT NULL,
  `tidspunt` time DEFAULT NULL,
  `varighet` int(11) DEFAULT NULL,
  `personligform` int(11) DEFAULT NULL,
  `prestasjon` int(11) DEFAULT NULL,
  `notat` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `workout`
--

INSERT INTO `workout` (`dato`, `tidspunt`, `varighet`, `personligform`, `prestasjon`, `notat`) VALUES
('2008-08-08', '16:00:00', 120, 5, 5, 'bra okt'),
('2012-03-02', '16:22:22', 2, 2, 2, 'TEST'),
('2018-03-20', '15:00:00', 2, 5, 7, 'Følte meg bra denne dagen, godt utbytte'),
('2018-03-21', '16:00:00', 120, 9, 9, 'Veldig bra'),
('2018-03-22', '10:00:00', 4, 8, 5, 'Sliten, men er fornøyd med å komme meg opp. Dårlig motivasjon på morgenen, så bra jeg kom meg opp'),
('2018-06-06', '12:12:12', 3, 3, 3, 'kristoffer'),
('2018-07-08', '14:00:00', 2, 5, 2, 'Dårlig'),
('2024-06-06', '12:13:16', 3, 4, 5, 'kristofffer'),
('2044-07-12', '14:15:15', 2, 4, 4, 'kissa'),
('3897-05-04', '04:04:04', 1, 4, 7, 'TETSTSTSTSTSTSTST'),
('3918-05-12', '14:00:00', 3, 6, 5, 'Braøkt'),
('3918-08-08', '14:00:00', 2, 5, 2, 'Dårlig'),
('3918-08-16', '14:00:00', 3, 6, 6, 'Sliten');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `workoutcontainsexercise`
--

CREATE TABLE `workoutcontainsexercise` (
  `dato` date NOT NULL,
  `navn` varchar(30) NOT NULL,
  `kilo` int(11) DEFAULT NULL,
  `sett` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dataark for tabell `workoutcontainsexercise`
--

INSERT INTO `workoutcontainsexercise` (`dato`, `navn`, `kilo`, `sett`) VALUES
('2018-03-20', 'Benkpress', 80, 3),
('2018-03-20', 'Knebøy', 100, 3),
('2018-03-20', 'Markløft', 120, 4),
('2018-03-21', 'Benkpress', 120, 5),
('2018-07-08', 'Benkpress', 80, 5),
('3897-05-04', 'Dips', 10, 3),
('3918-05-12', 'benkpress', 80, 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `exercise`
--
ALTER TABLE `exercise`
  ADD PRIMARY KEY (`navn`);

--
-- Indexes for table `exercisegroup`
--
ALTER TABLE `exercisegroup`
  ADD PRIMARY KEY (`navn`);

--
-- Indexes for table `exerciseonmachine`
--
ALTER TABLE `exerciseonmachine`
  ADD PRIMARY KEY (`exercisename`,`machinename`),
  ADD KEY `machinename` (`machinename`);

--
-- Indexes for table `groupcontainsexersice`
--
ALTER TABLE `groupcontainsexersice`
  ADD PRIMARY KEY (`gruppenavn`,`exersicename`),
  ADD KEY `exersicename` (`exersicename`);

--
-- Indexes for table `machine`
--
ALTER TABLE `machine`
  ADD PRIMARY KEY (`navn`);

--
-- Indexes for table `workout`
--
ALTER TABLE `workout`
  ADD PRIMARY KEY (`dato`);

--
-- Indexes for table `workoutcontainsexercise`
--
ALTER TABLE `workoutcontainsexercise`
  ADD PRIMARY KEY (`dato`,`navn`),
  ADD KEY `navn` (`navn`);

--
-- Begrensninger for dumpede tabeller
--

--
-- Begrensninger for tabell `exerciseonmachine`
--
ALTER TABLE `exerciseonmachine`
  ADD CONSTRAINT `exerciseonmachine_ibfk_1` FOREIGN KEY (`exercisename`) REFERENCES `exercise` (`navn`),
  ADD CONSTRAINT `exerciseonmachine_ibfk_2` FOREIGN KEY (`machinename`) REFERENCES `machine` (`navn`);

--
-- Begrensninger for tabell `groupcontainsexersice`
--
ALTER TABLE `groupcontainsexersice`
  ADD CONSTRAINT `groupcontainsexersice_ibfk_1` FOREIGN KEY (`gruppenavn`) REFERENCES `exercisegroup` (`navn`),
  ADD CONSTRAINT `groupcontainsexersice_ibfk_2` FOREIGN KEY (`exersicename`) REFERENCES `exercise` (`navn`);

--
-- Begrensninger for tabell `workoutcontainsexercise`
--
ALTER TABLE `workoutcontainsexercise`
  ADD CONSTRAINT `workoutcontainsexercise_ibfk_1` FOREIGN KEY (`dato`) REFERENCES `workout` (`dato`),
  ADD CONSTRAINT `workoutcontainsexercise_ibfk_2` FOREIGN KEY (`navn`) REFERENCES `exercise` (`navn`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
