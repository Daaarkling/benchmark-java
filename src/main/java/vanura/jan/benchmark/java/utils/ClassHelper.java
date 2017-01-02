/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanura.jan.benchmark.java.utils;



/**
 *
 * @author Jan
 */
public class ClassHelper {
	
	
	/**
	 * Attempt to create object from given className and check if implements instanceof
	 * 
	 * @param className the fully qualified name of the class
	 * @param interfaceName the .class of the interface
	 * @return Object can be cast into interfaceName
	 */
	public static Object instantiateClass(String className, Class<?> interfaceName) {

		try {
			Class clazz = Class.forName(className);
			if(interfaceName.isAssignableFrom(clazz)) {
				return clazz.newInstance();
			}
			return null;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoClassDefFoundError ex) {
			return null;
		}
	}

			
}
