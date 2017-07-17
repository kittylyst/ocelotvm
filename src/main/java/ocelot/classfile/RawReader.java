package ocelot.classfile;

/**
 *
 * @author ben
 */
public class RawReader {

    public OcelotClassReader readKlazz(byte[] contents, String fName) throws ClassNotFoundException {
        OcelotClassReader klazz = new OcelotClassReader(contents, fName);
	
        /*

	fread(&klazz.methods_count, sizeof(klazz.methods_count), 1, klazz_file.file);
	klazz.methods_count = be16toh(klazz.methods_count);

	klazz.methods = calloc(klazz.methods_count, sizeof(Method));
	Method *m;
	idx = 0;
	while (idx < klazz.methods_count) {
		m = klazz.methods + idx;
		fread(&m.flags, sizeof(u2), 1, klazz_file.file);
		fread(&m.name_idx, sizeof(u2), 1, klazz_file.file);
		fread(&m.desc_idx, sizeof(u2), 1, klazz_file.file);
		fread(&m.attrs_count, sizeof(u2), 1, klazz_file.file);
		m.name_idx = be16toh(m.name_idx);
		m.desc_idx = be16toh(m.desc_idx);
		m.attrs_count = be16toh(m.attrs_count);
		m.attrs = calloc(m.attrs_count, sizeof(Attribute));

		int aidx = 0;
		while (aidx < m.attrs_count) {
			parseAttribute(klazz_file, m.attrs + aidx);
			aidx++;
		}
		idx++;
	}

	fread(&klazz.attributes_count, sizeof(klazz.attributes_count), 1, klazz_file.file);
	klazz.attributes_count = be16toh(klazz.attributes_count);

	klazz.attributes = calloc(klazz.attributes_count, sizeof(Attribute));
	idx = 0;
	while (idx < klazz.attributes_count) {
		parseAttribute(klazz_file, klazz.attributes + idx);
		idx++;
	}
        */
        
	return klazz;
}

}
