drop database if exists `notes-project`;

create database if not exists `notes-project`;

use `notes-project`;

--
-- Table structure for table `users`
--

drop table if exists `users`;
create table `users` (
	`id` int(11) not null,
	`username` varchar(50) NOT NULL UNIQUE,
	`password` char(68) NOT NULL,
	`enabled` tinyint(1) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Inserting the only user, for testing
--password - foo
insert into `users` values(
	1,'foo',
    '$2a$10$55JJFhcepIs5PHXrQWzTVegFbp673KoSMdm95OOufbhX2dW3yxpri',1);

    
drop table if exists `authorities`;
create table `authorities` (
	`username` varchar(50) NOT NULL,
	`authority` varchar(50) NOT NULL,
	UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
	CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Inserting role for the only user

insert into `authorities` values(
	'foo',
    'ADMIN'
);

-- Table for storing notes
drop table if exists `notes`;
create table `notes` (
	`note_id` varchar(36) not null,
    `note_heading` varchar(100) not null,
    `note_body` text not null,
    `user_id` varchar(50) not null,
    `last_updated` long not null,
    primary key(`note_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;