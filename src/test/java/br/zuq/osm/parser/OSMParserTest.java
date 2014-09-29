package br.zuq.osm.parser;

import br.zuq.osm.parser.model.OSM;
import br.zuq.osm.parser.model.Relation;
import br.zuq.osm.parser.model.Way;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author zuq
 */
public class OSMParserTest {

    public OSMParserTest() {
    }

    @Test
    public void testTest() throws Exception {

        InputStream bis = new BufferedInputStream(getClass().getResourceAsStream("/montenegro-latest.osm.bz2"));
        CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);

        OSM osm = OSMParser.parse(input);
        assertEquals(990250, osm.getNodes().size());
        assertEquals(61263, osm.getWays().size());
        assertEquals(517, osm.getRelations().size());

        boolean found = false;
        for (Way w : osm.getWays()) {
            if (w.getName() != null) {
                if (w.getName().equals("Бучичко гробље")) {
                    assertTrue(w.getAllTags().containsKey("religion"));
                    assertTrue(w.getAllTags().containsKey("name"));
                    assertTrue(w.getAllTags().containsKey("landuse"));
                    assertTrue(w.getAllTags().containsKey("denomination"));
                    found = true;
                }
            }
        }
        assertTrue(found);
        found = false;
        for (Relation r : osm.getRelations()) {
            if (r.getName() != null && r.getName().equals("Čapljina - Hum - Dubrovnik")) {
                assertTrue(r.getAllTags().containsKey("railway"));
                assertTrue(r.getAllTags().containsKey("route"));
                assertTrue(r.getAllTags().containsKey("type"));
                found = true;
            }
        }
        assertTrue(found);
    }
}
