package hibernate_integration_test_false_positives;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class FalsePositiveDemoTest {

    @PersistenceContext(unitName="shipmentOrder")
    EntityManager shipmentOrderEntityManager;

    @Test
    public void incorrectly_passes_successfully() {
        ShipmentOrder shipmentOrder = new ShipmentOrder();
        shipmentOrder.setCustomerName("CUSTOMER");
        shipmentOrder.getDeliveryAddress().setCountry("COUNTRY");
        shipmentOrderEntityManager.persist(shipmentOrder);

        shipmentOrderEntityManager.flush();
        // uncomment line below to purge 1st level cache and catch our mapping problem
//        shipmentOrderEntityManager.clear();

        ShipmentOrder foundShipmentOrder = shipmentOrderEntityManager.find(ShipmentOrder.class, shipmentOrder.getId());
        assertEquals(shipmentOrder, foundShipmentOrder);
    }

    @AfterTransaction
    public void ensureTransactionRolledBack() {
        List resultList = findAll();
        assertEquals(0, resultList.size());
    }

    private List findAll() {
        Query query = shipmentOrderEntityManager.createQuery("SELECT shipmentOrder FROM ShipmentOrder shipmentOrder");
        final List resultList = query.getResultList();
        return resultList;
    }
}
