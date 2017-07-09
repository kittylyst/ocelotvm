package ocelot.classfile;

/**
 *
 * @author ben
 */
public class RawReader {

    public OcelotClassReader readKlazz(byte[] contents, String fName) throws ClassNotFoundException {
        OcelotClassReader klazz = new OcelotClassReader(contents, fName);
	klazz.parseHeader();
        klazz.parseConstantPool();
	
        /*

	fread(&klazz.fields_count, sizeof(klazz.fields_count), 1, klazz_file.file);
	klazz.fields_count = be16toh(klazz.fields_count);

	klazz.fields = calloc(klazz.fields_count, sizeof(Field));
	Field *f;
	idx = 0;
	while (idx < klazz.fields_count) {
		f = klazz.fields + idx;
		fread(&f.flags, sizeof(u2), 1, klazz_file.file);
		fread(&f.name_idx, sizeof(u2), 1, klazz_file.file);
		fread(&f.desc_idx, sizeof(u2), 1, klazz_file.file);
		fread(&f.attrs_count, sizeof(u2), 1, klazz_file.file);
		f.name_idx = be16toh(f.name_idx);
		f.desc_idx = be16toh(f.desc_idx);
		f.attrs_count = be16toh(f.attrs_count);
		f.attrs = calloc(f.attrs_count, sizeof(Attribute));

		int aidx = 0;
		while (aidx < f.attrs_count) {
			parse_attribute(klazz_file, f.attrs + aidx);
			aidx++;
		}
		idx++;
	}

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
			parse_attribute(klazz_file, m.attrs + aidx);
			aidx++;
		}
		idx++;
	}

	fread(&klazz.attributes_count, sizeof(klazz.attributes_count), 1, klazz_file.file);
	klazz.attributes_count = be16toh(klazz.attributes_count);

	klazz.attributes = calloc(klazz.attributes_count, sizeof(Attribute));
	idx = 0;
	while (idx < klazz.attributes_count) {
		parse_attribute(klazz_file, klazz.attributes + idx);
		idx++;
	}
        */
        
	return klazz;
}


/*
void parse_attribute(byte[] klazz_file, Attribute *attr) {
	fread(&attr.name_idx, sizeof(u2), 1, klazz_file.file);
	fread(&attr.length, sizeof(u4), 1, klazz_file.file);
	attr.name_idx = be16toh(attr.name_idx);
	attr.length = be32toh(attr.length);
	attr.info = calloc(attr.length + 1, sizeof(char));
	fread(attr.info, sizeof(char), attr.length, klazz_file.file);
	attr.info[attr.length] = '\0';
}
*/
    
}
