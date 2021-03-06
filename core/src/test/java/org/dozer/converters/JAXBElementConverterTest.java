/**
 * Copyright 2005-2013 Dozer Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dozer.converters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.dozer.AbstractDozerTest;
import org.dozer.MappingException;
import org.dozer.vo.jaxb.employee.EmployeeType;
import org.dozer.vo.jaxb.employee.EmployeeWithInnerClass;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jose Barragan
 */
public class JAXBElementConverterTest extends AbstractDozerTest {

	public static final int YEAR  = 101;
	public static final int MONTH = 1;
	public static final int DAY   = 1;

	private JAXBElementConverter converter;

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void stringToJAXBElementConversion() throws Exception {
		converter = new JAXBElementConverter(EmployeeWithInnerClass.class.getCanonicalName(), "parentName", new SimpleDateFormat("dd.MM.yyyy"));
		Object conversion = converter.convert(JAXBElement.class, "dummy");
		assertNotNull(conversion);
		assertEquals("javax.xml.bind.JAXBElement", conversion.getClass().getCanonicalName());
		assertEquals("java.lang.String", JAXBElement.class.cast(conversion).getDeclaredType().getCanonicalName());
		assertEquals("dummy", JAXBElement.class.cast(conversion).getValue());
	}

	@Test
	public void xmlgregoriancalendarToJAXBElementConversion() throws Exception {
		converter = new JAXBElementConverter(EmployeeType.class.getCanonicalName(), "birthDate", new SimpleDateFormat("dd.MM.yyyy"));
		DatatypeFactory instance = DatatypeFactory.newInstance();
		XMLGregorianCalendar calendar = instance.newXMLGregorianCalendar(new GregorianCalendar(2001, 1, 1));
		Object conversion = converter.convert(JAXBElement.class, calendar);
		assertNotNull(conversion);
		assertEquals("javax.xml.bind.JAXBElement", conversion.getClass().getCanonicalName());
		assertEquals("javax.xml.datatype.XMLGregorianCalendar", JAXBElement.class.cast(conversion).getDeclaredType().getCanonicalName());
		assertEquals(calendar.toString(), JAXBElement.class.cast(conversion).getValue().toString());
	}

	@Test
	public void calendarToJAXBElementConversion() throws Exception {
		converter = new JAXBElementConverter(EmployeeType.class.getCanonicalName(), "birthDate", new SimpleDateFormat("dd.MM.yyyy"));
		Calendar calendar = new GregorianCalendar(2001, 1, 1);
		Object conversion = converter.convert(JAXBElement.class, calendar);
		assertNotNull(conversion);
		assertEquals("javax.xml.bind.JAXBElement", conversion.getClass().getCanonicalName());
		assertEquals("javax.xml.datatype.XMLGregorianCalendar", JAXBElement.class.cast(conversion).getDeclaredType().getCanonicalName());
		DatatypeFactory instance = DatatypeFactory.newInstance();
		XMLGregorianCalendar expected = instance.newXMLGregorianCalendar(new GregorianCalendar(2001, 1, 1));
		assertEquals(expected.toString(), JAXBElement.class.cast(conversion).getValue().toString());
	}

	@Test
	public void dateToJAXBElementConversion() throws Exception {
		converter = new JAXBElementConverter(EmployeeType.class.getCanonicalName(), "birthDate", new SimpleDateFormat("dd.MM.yyyy"));
		Date calendar = new Date(YEAR, MONTH, DAY);
		Object conversion = converter.convert(JAXBElement.class, calendar);
		assertNotNull(conversion);
		assertEquals("javax.xml.bind.JAXBElement", conversion.getClass().getCanonicalName());
		assertEquals("javax.xml.datatype.XMLGregorianCalendar", JAXBElement.class.cast(conversion).getDeclaredType().getCanonicalName());
		DatatypeFactory instance = DatatypeFactory.newInstance();
		XMLGregorianCalendar expected = instance.newXMLGregorianCalendar(new GregorianCalendar(2001, 1, 1));
		assertEquals(expected.toString(), JAXBElement.class.cast(conversion).getValue().toString());
	}

	@Test
	public void dateToJAXBElementStringConversion() throws Exception {
		converter = new JAXBElementConverter(EmployeeWithInnerClass.class.getCanonicalName(), "parentName", new SimpleDateFormat("dd.MM.yyyy"));
		Date calendar = new Date(YEAR, MONTH, DAY);
		Object conversion = converter.convert(JAXBElement.class, calendar);
		assertNotNull(conversion);
		assertEquals("javax.xml.bind.JAXBElement", conversion.getClass().getCanonicalName());
		assertEquals("java.lang.String", JAXBElement.class.cast(conversion).getDeclaredType().getCanonicalName());
		assertEquals("01.02.2001", JAXBElement.class.cast(conversion).getValue());
	}

	@Test
	public void calendarToJAXBElementStringConversion() throws Exception {
		converter = new JAXBElementConverter(EmployeeWithInnerClass.class.getCanonicalName(), "parentName", new SimpleDateFormat("dd.MM.yyyy"));
		Calendar calendar = new GregorianCalendar(2001, 1, 1);
		Object conversion = converter.convert(JAXBElement.class, calendar);
		assertNotNull(conversion);
		assertEquals("javax.xml.bind.JAXBElement", conversion.getClass().getCanonicalName());
		assertEquals("java.lang.String", JAXBElement.class.cast(conversion).getDeclaredType().getCanonicalName());
		assertEquals("01.02.2001", JAXBElement.class.cast(conversion).getValue());
	}

	@Test(expected = MappingException.class)
	public void createFieldNotFoundException() throws Exception {
		converter = new JAXBElementConverter(EmployeeWithInnerClass.class.getCanonicalName(), "vds", new SimpleDateFormat("dd.MM.yyyy"));
		converter.convert(JAXBElement.class, "dummy");
	}

	@Test
	public void stringTypeBeanId() throws Exception {
		converter = new JAXBElementConverter(EmployeeWithInnerClass.class.getCanonicalName(), "parentName", new SimpleDateFormat("dd.MM.yyyy"));
		String beanId = converter.getBeanId();
		assertNotNull(beanId);
		assertEquals("java.lang.String", beanId);
	}

	@Test
	public void xmlgregoriancalendarTypeBeanId() throws Exception {
		converter = new JAXBElementConverter(EmployeeType.class.getCanonicalName(), "birthDate", new SimpleDateFormat("dd.MM.yyyy"));
		String beanId = converter.getBeanId();
		assertNotNull(beanId);
		assertEquals("javax.xml.datatype.XMLGregorianCalendar", beanId);
	}

	@Test(expected = MappingException.class)
	public void beanIdFieldNotFoundException() throws Exception {
		converter = new JAXBElementConverter(EmployeeWithInnerClass.class.getCanonicalName(), "vds", new SimpleDateFormat("dd.MM.yyyy"));
		converter.getBeanId();
	}
}
