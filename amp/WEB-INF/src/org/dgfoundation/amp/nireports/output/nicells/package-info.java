/**
 * This package contains <i>nicell</i>s and related interfaces. <br />
 * A <i>nicell</i> is a descendant of class {@link org.dgfoundation.amp.nireports.output.nicells.NiOutCell} which holds information in aggregated state, 
 * e.g. a {@link org.dgfoundation.amp.nireports.output.nicells.NiOutCell} is the result of putting together 0, 1 or more {@link org.dgfoundation.amp.nireports.runtime.NiCell} instances through
 * an operation of <i>reduction</i>. The specification of this transformation depends on the measure the initial {@link org.dgfoundation.amp.nireports.runtime.NiCell}s were generated by<br />
 * Please note that reduction always happens within {@link org.dgfoundation.amp.nireports.runtime.NiCell} instances coming from the same entity, 
 * thus {@link org.dgfoundation.amp.nireports.schema.NiReportedEntity#getBehaviour()} governs it all. <br />
 * Being aggregated cells, {@link org.dgfoundation.amp.nireports.output.nicells.NiOutCell} and descendants lack many of the attributes of an ordinary disaggregated {@link org.dgfoundation.amp.nireports.output.nicells.CellVisitor}: 
 * <ul>
 *  <li>they lack coordinates</li>
 *  <li>they lack metadata</li>
 *  <li>they lack ownerIds</li>
 * </ul>   
 */
package org.dgfoundation.amp.nireports.output.nicells;
