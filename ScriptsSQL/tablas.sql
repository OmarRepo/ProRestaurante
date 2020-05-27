CREATE TABLE EMPLEADOS(
	ID_Empleado VARCHAR2(5),
	DNI VARCHAR2(9),
	Nombre VARCHAR2(15),
	Apellidos VARCHAR2(30),
	Username VARCHAR2(5),
	Tipo VARCHAR(8),
	CONSTRAINT empleado_pk PRIMARY KEY(ID_Empleado),
	CONSTRAINT empleado_tipo CHECK(Tipo IN('Jefe','Cocinero','Camarero'))
);

CREATE TABLE PEDIDOS (
	ID_Pedido VARCHAR2(5),
	Mesa NUMBER(2),
	Fecha_Creacion DATE,
	Estado VARCHAR2(10),
	ID_Camarero VARCHAR2(5),
	ID_Cocinero VARCHAR2(5),
	CONSTRAINT pedidos_pk PRIMARY KEY (ID_Pedido),
	CONSTRAINT pedidos_fk1 FOREIGN KEY (ID_Cocinero) 
	REFERENCES  empleados(ID_Empleado) ON DELETE SET NULL,
	CONSTRAINT pedidos_fk2 FOREIGN KEY (ID_Camarero) 
	REFERENCES  empleados(ID_Empleado) ON DELETE SET NULL
);


CREATE TABLE CONSUMIBLES(
	ID_Consumible VARCHAR2(5),
	Nombre VARCHAR2(20),
	Precio NUMBER(3,3),
	Tipo VARCHAR2(6) NOT NULL,
	CONSTRAINT consumibles_pk PRIMARY KEY(ID_Consumible),
	CONSTRAINT consumibles_tipo CHECK(Tipo IN('Plato','Menu','Bebida'))
);

CREATE TABLE MENUS(
	ID_Menu VARCHAR2(5),
	CONSTRAINT menus_pk PRIMARY KEY(ID_Menu),
	CONSTRAINT menus_fk FOREIGN KEY(ID_Menu) 
	REFERENCES Consumibles(ID_Consumible) ON DELETE CASCADE
);

CREATE TABLE PLATOS(
	ID_Plato VARCHAR2(5),
	CONSTRAINT platos_pk PRIMARY KEY(ID_Plato),
	CONSTRAINT platos_fk FOREIGN KEY(ID_Plato)
	REFERENCES Consumibles(ID_Consumible) ON DELETE CASCADE
);

CREATE TABLE BEBIDAS(
	ID_Bebida VARCHAR2(5),
	Almacenado NUMBER(4) NOT NULL,
	CONSTRAINT bebidas_pk PRIMARY KEY(ID_Bebida),
	CONSTRAINT bebida_fk FOREIGN KEY(ID_Bebida) 
	REFERENCES Consumibles(ID_Consumible) ON DELETE CASCADE
);

CREATE TABLE INGREDIENTES(
	ID_Ingrediente VARCHAR(5),
	Nombre VARCHAR(20),
	Almacenado NUMBER(4) NOT NULL,
	CONSTRAINT ingredientes_pk PRIMARY KEY(ID_Ingrediente)
);

CREATE TABLE PEDIDOS_CONSUMIBLES(
	ID_Pedido VARCHAR2(5),
	ID_Consumible VARCHAR2(5),
	Cantidad NUMBER(2) NOT NULL,
	CONSTRAINT p_c_pk PRIMARY KEY(ID_Pedido,ID_Consumible),
	CONSTRAINT p_c_fk1 FOREIGN KEY(ID_Pedido)
	REFERENCES Pedidos(ID_Pedido) ON DELETE CASCADE,
	CONSTRAINT p_c_fk2  FOREIGN KEY(ID_Consumible)
	REFERENCES Consumibles(ID_Consumible) ON DELETE CASCADE
);

CREATE TABLE MENUS_CONSUMIBLES(
	ID_Menu VARCHAR(5),
	ID_Consumible VARCHAR(5),
	Cantidad NUMBER(2) NOT NULL,
	CONSTRAINT m_c_pk PRIMARY KEY(ID_Menu,ID_Consumible),
	CONSTRAINT m_c_fk1 FOREIGN KEY(ID_Menu) 
	REFERENCES Menus(ID_Menu) ON DELETE CASCADE,
	CONSTRAINT m_c_fk2 FOREIGN KEY(ID_Consumible) 
	REFERENCES Consumibles(ID_Consumible) ON DELETE CASCADE
	
);

CREATE TABLE PLATO_INGREDIENTES(
	ID_Plato VARCHAR(5),
	ID_Ingrediente VARCHAR(5),
	Cantidad NUMBER(2) NOT NULL,
	CONSTRAINT p_i_pk PRIMARY KEY(ID_Plato,ID_Ingrediente),
	CONSTRAINT p_i_fk1 FOREIGN KEY(ID_Plato) 
	REFERENCES Platos(ID_Plato) ON DELETE CASCADE,
	CONSTRAINT p_i_fk2 FOREIGN KEY(ID_Ingrediente) 
	REFERENCES Ingredientes(ID_Ingrediente) ON DELETE CASCADE
);









