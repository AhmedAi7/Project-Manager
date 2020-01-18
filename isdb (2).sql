-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 23, 2019 at 08:17 PM
-- Server version: 8.0.17
-- PHP Version: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `isdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `depend`
--

CREATE TABLE `depend` (
  `TaskID` int(11) NOT NULL,
  `dTaskID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employ`
--

CREATE TABLE `employ` (
  `EmID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Job` varchar(50) NOT NULL,
  `hrsday` int(11) NOT NULL,
  `NumofTasks` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `employ`
--

INSERT INTO `employ` (`EmID`, `Name`, `Job`, `hrsday`, `NumofTasks`) VALUES
(1, 'Ahmed', 'ff', 12, 0);

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

CREATE TABLE `project` (
  `ProID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Start` date NOT NULL,
  `Due` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `project`
--

INSERT INTO `project` (`ProID`, `Name`, `Start`, `Due`) VALUES
(1, 'test1', '2019-12-01', '2019-12-24'),
(2, 'Test2', '2020-02-05', '2020-06-25');

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `TaskID` int(11) NOT NULL,
  `TName` varchar(50) NOT NULL,
  `ProID` int(11) NOT NULL,
  `EmID` int(11) NOT NULL,
  `Duration` float NOT NULL,
  `Parent` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tasks`
--

INSERT INTO `tasks` (`TaskID`, `TName`, `ProID`, `EmID`, `Duration`, `Parent`) VALUES
(1, 'Task1', 2, 1, 30, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `depend`
--
ALTER TABLE `depend`
  ADD KEY `F5` (`TaskID`,`dTaskID`),
  ADD KEY `dTaskID` (`dTaskID`);

--
-- Indexes for table `employ`
--
ALTER TABLE `employ`
  ADD PRIMARY KEY (`EmID`);

--
-- Indexes for table `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`ProID`);

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`TaskID`),
  ADD KEY `ProID` (`ProID`),
  ADD KEY `EmID` (`EmID`),
  ADD KEY `f6` (`Parent`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `depend`
--
ALTER TABLE `depend`
  ADD CONSTRAINT `depend_ibfk_1` FOREIGN KEY (`TaskID`) REFERENCES `tasks` (`TaskID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `depend_ibfk_2` FOREIGN KEY (`dTaskID`) REFERENCES `tasks` (`TaskID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `tasks`
--
ALTER TABLE `tasks`
  ADD CONSTRAINT `F1` FOREIGN KEY (`ProID`) REFERENCES `project` (`ProID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `F2` FOREIGN KEY (`EmID`) REFERENCES `employ` (`EmID`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
