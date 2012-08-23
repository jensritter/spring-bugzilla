/**
 * This package groups all of the exceptions which the library can throw during the
 * normal course of use. It includes {@link jbugz.exceptions.BugzillaException}
 * and its subclasses, which are thrown based on faults returned by the underlying
 * XML document, as well as {@link jbugz.exceptions.ConnectionException}, thrown
 * by a failure to properly connect to an installation. Another such exception
 * is {@link jbugz.exceptions.InvalidDescriptionException}, thrown when a client
 * attempts to create a {@link de.mitegro.bugzilla.j2bugzilla.Bug} without all required fields.
 */
package com.google.code.SpringBug.exceptions;