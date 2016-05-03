package presentation;

import dataaccess.Persistence;

/**
 * Handles transactions. 
 * Requests are dispatched and handled by table modules.
 * Read Martin Fowler's Layer Supertype pattern. 
 * 
 * @author fmartins
 * @version 1.1 (5/10/2014)
 */
public class Service {
	
	/**
	 * The persistence repository. Notice it is visible in its package, with 
	 * is less than the protected visibility.
	 */
	Persistence persistence;

	/**
	 * Constructs a service given its persistence repository
	 * 
	 * @param persistence The persistence repository of the service.
	 */
	public Service(Persistence persistence) {
		this.persistence = persistence;
	}
}
