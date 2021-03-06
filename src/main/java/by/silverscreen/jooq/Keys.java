/**
 * This class is generated by jOOQ
 */
package by.silverscreen.jooq;

/**
 * This class is generated by jOOQ.
 *
 * A class modelling foreign key relationships between tables of the <code>public</code> 
 * schema
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.Identity<by.silverscreen.jooq.tables.records.UserRolesRecord, java.lang.Short> IDENTITY_USER_ROLES = Identities0.IDENTITY_USER_ROLES;

	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.UniqueKey<by.silverscreen.jooq.tables.records.NotificationsRecord> UNIQ_LOGIN = UniqueKeys0.UNIQ_LOGIN;
	public static final org.jooq.UniqueKey<by.silverscreen.jooq.tables.records.PhoneRecord> PHONE_PK = UniqueKeys0.PHONE_PK;
	public static final org.jooq.UniqueKey<by.silverscreen.jooq.tables.records.UserRolesRecord> USER_ROLES_PKEY = UniqueKeys0.USER_ROLES_PKEY;
	public static final org.jooq.UniqueKey<by.silverscreen.jooq.tables.records.UserRolesRecord> USER_ROLES_ROLE_USERNAME_KEY = UniqueKeys0.USER_ROLES_ROLE_USERNAME_KEY;
	public static final org.jooq.UniqueKey<by.silverscreen.jooq.tables.records.UsersRecord> USERS_PKEY = UniqueKeys0.USERS_PKEY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------


	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class Identities0 extends org.jooq.impl.AbstractKeys {
		public static org.jooq.Identity<by.silverscreen.jooq.tables.records.UserRolesRecord, java.lang.Short> IDENTITY_USER_ROLES = createIdentity(by.silverscreen.jooq.tables.UserRoles.USER_ROLES, by.silverscreen.jooq.tables.UserRoles.USER_ROLES.USER_ROLE_ID);
	}

	private static class UniqueKeys0 extends org.jooq.impl.AbstractKeys {
		public static final org.jooq.UniqueKey<by.silverscreen.jooq.tables.records.NotificationsRecord> UNIQ_LOGIN = createUniqueKey(by.silverscreen.jooq.tables.Notifications.NOTIFICATIONS, by.silverscreen.jooq.tables.Notifications.NOTIFICATIONS.ID);
		public static final org.jooq.UniqueKey<by.silverscreen.jooq.tables.records.PhoneRecord> PHONE_PK = createUniqueKey(by.silverscreen.jooq.tables.Phone.PHONE, by.silverscreen.jooq.tables.Phone.PHONE.DATA_ID);
		public static final org.jooq.UniqueKey<by.silverscreen.jooq.tables.records.UserRolesRecord> USER_ROLES_PKEY = createUniqueKey(by.silverscreen.jooq.tables.UserRoles.USER_ROLES, by.silverscreen.jooq.tables.UserRoles.USER_ROLES.USER_ROLE_ID);
		public static final org.jooq.UniqueKey<by.silverscreen.jooq.tables.records.UserRolesRecord> USER_ROLES_ROLE_USERNAME_KEY = createUniqueKey(by.silverscreen.jooq.tables.UserRoles.USER_ROLES, by.silverscreen.jooq.tables.UserRoles.USER_ROLES.ROLE, by.silverscreen.jooq.tables.UserRoles.USER_ROLES.USERNAME);
		public static final org.jooq.UniqueKey<by.silverscreen.jooq.tables.records.UsersRecord> USERS_PKEY = createUniqueKey(by.silverscreen.jooq.tables.Users.USERS, by.silverscreen.jooq.tables.Users.USERS.USERNAME);
	}
}
