-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-03-2022 a las 05:47:02
-- Versión del servidor: 10.4.22-MariaDB
-- Versión de PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `contracts`
--
CREATE DATABASE IF NOT EXISTS `contracts` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `contracts`;

--
-- Volcado de datos para la tabla `contrato`
--

INSERT INTO `contrato` (`id`, `id_proveedor`, `id_dictamen`, `numero`, `duracion`, `fecha_firma`, `fecha_venc`, `observaciones`) VALUES
(1, 3, 1, NULL, NULL, NULL, NULL, NULL),
(2, 1, NULL, NULL, NULL, NULL, NULL, NULL),
(3, 2, 2, '2022/01', 10, '2022-01-10', '2022-11-10', 'Contrato para la compra de producciones textiles: ropa, cortinas, sabanas...'),
(4, 1, 3, '2022/12', 3, '2021-12-23', '2022-03-23', 'Ok'),
(5, 3, 4, '2012/34', 10, '2012-06-21', '2013-04-21', 'Viejo'),
(6, 2, 5, '2021/23', 5, '2021-04-22', '2021-09-22', 'Okkkkk');

--
-- Volcado de datos para la tabla `dictamen`
--

INSERT INTO `dictamen` (`id`, `numero`, `aprobado`, `observaciones`) VALUES
(1, '2022/023', 0, 'No aprobado por razones de higiene.'),
(2, '2022/024', 1, 'Todo en orden'),
(3, '2022/21', 1, 'Todo ok otra vez'),
(4, '2022/92', 1, 'Ahora si se aprueba...'),
(5, '2021/23', 0, 'Contato de prueba viejo'),
(6, '2021/23', 1, 'Contato de prueba viejo');

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`id`, `nombre`, `activo`) VALUES
(1, 'Salud Públicas', 1),
(2, 'Taller Textil', 1),
(3, 'Empresa Porcino', 1);

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `nombre`, `apellidoP`, `apellidoM`, `email`, `telefono`, `enabled`) VALUES
(3, 'admin', '$2a$10$UZgor7s2J0d.n8d8.0Vl2.pQBsswH8I8bE9bPwJ9h.rUrClxO/FRa', 'Admin', 'Prueba', 'Numero', 'admin@admin.co', NULL, 1),
(6, 'danny98cuba', '$2a$10$WEGO0TNl1TQbqeq6wrSUvebDvDUUiVw8TH7atiUm6hd85vMb2EXia', 'Daniel', 'González', 'Cuétara', 'danny.glezcuet@gmail.com', '53741292', 1);

--
-- Volcado de datos para la tabla `user_role`
--

INSERT INTO `user_role` (`user_id`, `roles_id`) VALUES
(3, 1),
(6, 2),
(6, 3);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
