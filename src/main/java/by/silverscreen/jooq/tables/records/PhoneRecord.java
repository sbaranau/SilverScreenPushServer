/**
 * This class is generated by jOOQ
 */
package by.silverscreen.jooq.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PhoneRecord extends org.jooq.impl.UpdatableRecordImpl<by.silverscreen.jooq.tables.records.PhoneRecord> implements org.jooq.Record9<java.util.UUID, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String> {

	private static final long serialVersionUID = -1214658639;

	/**
	 * Setter for <code>public.phone.data_id</code>.
	 */
	public void setDataId(java.util.UUID value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.phone.data_id</code>.
	 */
	public java.util.UUID getDataId() {
		return (java.util.UUID) getValue(0);
	}

	/**
	 * Setter for <code>public.phone.token</code>.
	 */
	public void setToken(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.phone.token</code>.
	 */
	public java.lang.String getToken() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>public.phone.login</code>.
	 */
	public void setLogin(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>public.phone.login</code>.
	 */
	public java.lang.String getLogin() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>public.phone.password</code>.
	 */
	public void setPassword(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>public.phone.password</code>.
	 */
	public java.lang.String getPassword() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>public.phone.date</code>.
	 */
	public void setDate(java.lang.Long value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>public.phone.date</code>.
	 */
	public java.lang.Long getDate() {
		return (java.lang.Long) getValue(4);
	}

	/**
	 * Setter for <code>public.phone.birth</code>.
	 */
	public void setBirth(java.lang.Long value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>public.phone.birth</code>.
	 */
	public java.lang.Long getBirth() {
		return (java.lang.Long) getValue(5);
	}

	/**
	 * Setter for <code>public.phone.isman</code>.
	 */
	public void setIsman(java.lang.Long value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>public.phone.isman</code>.
	 */
	public java.lang.Long getIsman() {
		return (java.lang.Long) getValue(6);
	}

	/**
	 * Setter for <code>public.phone.name</code>.
	 */
	public void setName(java.lang.String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>public.phone.name</code>.
	 */
	public java.lang.String getName() {
		return (java.lang.String) getValue(7);
	}

	/**
	 * Setter for <code>public.phone.system</code>.
	 */
	public void setSystem(java.lang.String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>public.phone.system</code>.
	 */
	public java.lang.String getSystem() {
		return (java.lang.String) getValue(8);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.util.UUID> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record9 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row9<java.util.UUID, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String> fieldsRow() {
		return (org.jooq.Row9) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row9<java.util.UUID, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String> valuesRow() {
		return (org.jooq.Row9) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.util.UUID> field1() {
		return by.silverscreen.jooq.tables.Phone.PHONE.DATA_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return by.silverscreen.jooq.tables.Phone.PHONE.TOKEN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return by.silverscreen.jooq.tables.Phone.PHONE.LOGIN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return by.silverscreen.jooq.tables.Phone.PHONE.PASSWORD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field5() {
		return by.silverscreen.jooq.tables.Phone.PHONE.DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field6() {
		return by.silverscreen.jooq.tables.Phone.PHONE.BIRTH;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field7() {
		return by.silverscreen.jooq.tables.Phone.PHONE.ISMAN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field8() {
		return by.silverscreen.jooq.tables.Phone.PHONE.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field9() {
		return by.silverscreen.jooq.tables.Phone.PHONE.SYSTEM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.UUID value1() {
		return getDataId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getToken();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getLogin();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getPassword();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value5() {
		return getDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value6() {
		return getBirth();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value7() {
		return getIsman();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value8() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value9() {
		return getSystem();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PhoneRecord value1(java.util.UUID value) {
		setDataId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PhoneRecord value2(java.lang.String value) {
		setToken(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PhoneRecord value3(java.lang.String value) {
		setLogin(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PhoneRecord value4(java.lang.String value) {
		setPassword(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PhoneRecord value5(java.lang.Long value) {
		setDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PhoneRecord value6(java.lang.Long value) {
		setBirth(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PhoneRecord value7(java.lang.Long value) {
		setIsman(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PhoneRecord value8(java.lang.String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PhoneRecord value9(java.lang.String value) {
		setSystem(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PhoneRecord values(java.util.UUID value1, java.lang.String value2, java.lang.String value3, java.lang.String value4, java.lang.Long value5, java.lang.Long value6, java.lang.Long value7, java.lang.String value8, java.lang.String value9) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PhoneRecord
	 */
	public PhoneRecord() {
		super(by.silverscreen.jooq.tables.Phone.PHONE);
	}

	/**
	 * Create a detached, initialised PhoneRecord
	 */
	public PhoneRecord(java.util.UUID dataId, java.lang.String token, java.lang.String login, java.lang.String password, java.lang.Long date, java.lang.Long birth, java.lang.Long isman, java.lang.String name, java.lang.String system) {
		super(by.silverscreen.jooq.tables.Phone.PHONE);

		setValue(0, dataId);
		setValue(1, token);
		setValue(2, login);
		setValue(3, password);
		setValue(4, date);
		setValue(5, birth);
		setValue(6, isman);
		setValue(7, name);
		setValue(8, system);
	}
}
