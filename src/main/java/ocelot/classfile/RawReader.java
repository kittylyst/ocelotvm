package ocelot.classfile;

/**
 *
 * @author ben
 */
public class RawReader {

    public ClassEntry readKlazz(byte[] contents, String fName) {
        ClassEntry klazz = new ClassEntry(contents, fName);
	klazz.parseHeader();

//	parseConstantPool(klazz, klazz.const_pool_count, klazz_file);
	
        /*
	if (klazz.pool_size_bytes == 0) {
		return NULL;
	}

	fread(&klazz.flags, sizeof(klazz.flags), 1, klazz_file.file);
	klazz.flags = be16toh(klazz.flags);

	fread(&klazz.this_klazz, sizeof(klazz.this_klazz), 1, klazz_file.file);
	klazz.this_klazz = be16toh(klazz.this_klazz);

	fread(&klazz.super_klazz, sizeof(klazz.super_klazz), 1, klazz_file.file);
	klazz.super_klazz = be16toh(klazz.super_klazz);

	fread(&klazz.interfaces_count, sizeof(klazz.interfaces_count), 1, klazz_file.file);
	klazz.interfaces_count = be16toh(klazz.interfaces_count);

	klazz.interfaces = calloc(klazz.interfaces_count, sizeof(Ref));
	int idx = 0;
	while (idx < klazz.interfaces_count) {
		fread(&klazz.interfaces[idx].klazz_idx, sizeof(klazz.interfaces[idx].klazz_idx), 1, klazz_file.file);
		klazz.interfaces[idx].klazz_idx = be16toh(klazz.interfaces[idx].klazz_idx);
		idx++;
	}

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


//void parseConstantPool(ClassEntry klazz, int const_pool_count, byte[] klazz_file) {
//	const int MAX_ITEMS = const_pool_count - 1;
//	uint32_t table_size_bytes = 0;
//	int i;
//	char tag_byte;
//	Ref r;
//
//	klazz.items = calloc(MAX_ITEMS, sizeof(Class));
//	for (i = 1; i <= MAX_ITEMS; i++) {
//		fread(&tag_byte, sizeof(char), 1, klazz_file.file);
//		if (tag_byte < MIN_CPOOL_TAG || tag_byte > MAX_CPOOL_TAG) {
//			fprintf(stderr, "Tag byte '%d' is outside permitted range %u to %u\n", tag_byte, MIN_CPOOL_TAG, MAX_CPOOL_TAG);
//			table_size_bytes = 0;
//			break; // fail fast
//		}
//
//		String s;
//		uint16_t ptr_idx = i - 1;
//		Item *item = klazz.items + ptr_idx;
//		item.tag = tag_byte;
//
//		// Populate item based on tag_byte
//		switch (tag_byte) {
//			case STRING_UTF8: // String prefixed by a uint16 indicating the number of bytes in the encoded string which immediately follows
//				fread(&s.length, sizeof(s.length), 1, klazz_file.file);
//				s.length = be16toh(s.length);
//				s.value = malloc(sizeof(char) * s.length);
//				fread(s.value, sizeof(char), s.length, klazz_file.file);
//				item.value.string = s;
//				table_size_bytes += 2 + s.length;
//				break;
//			case INTEGER: // Integer: a signed 32-bit two's complement number in big-endian format
//				fread(&item.value.integer, sizeof(item.value.integer), 1, klazz_file.file);
//				item.value.integer = be32toh(item.value.integer);
//				table_size_bytes += 4;
//				break;
//			case FLOAT: // Float: a 32-bit single-precision IEEE 754 floating-point number
//				fread(&item.value.flt, sizeof(item.value.flt), 1, klazz_file.file);
//				item.value.flt = be32toh(item.value.flt);
//				table_size_bytes += 4;
//				break;
//			case LONG: // Long: a signed 64-bit two's complement number in big-endian format (takes two slots in the constant pool table)
//				fread(&item.value.lng.high, sizeof(item.value.lng.high), 1, klazz_file.file); // 4 bytes
//				fread(&item.value.lng.low, sizeof(item.value.lng.low), 1, klazz_file.file); // 4 bytes
//				item.value.lng.high = be32toh(item.value.lng.high);
//				item.value.lng.low = be32toh(item.value.lng.low);
//				// 8-byte consts take 2 pool entries
//				++i;
//				table_size_bytes += 8;
//				break;
//			case DOUBLE: // Double: a 64-bit double-precision IEEE 754 floating-point number (takes two slots in the constant pool table)
//				fread(&item.value.dbl.high, sizeof(item.value.dbl.high), 1, klazz_file.file); // 4 bytes
//				fread(&item.value.dbl.low, sizeof(item.value.dbl.low), 1, klazz_file.file); // 4 bytes
//				item.value.dbl.high = be32toh(item.value.dbl.high);
//				item.value.dbl.low = be32toh(item.value.dbl.low);
//				// 8-byte consts take 2 pool entries
//				++i;
//				table_size_bytes += 8;
//				break;
//			case CLASS: // Class reference: an uint16 within the constant pool to a UTF-8 string containing the fully qualified klazz name
//				fread(&r.klazz_idx, sizeof(r.klazz_idx), 1, klazz_file.file);
//				r.klazz_idx = be16toh(r.klazz_idx);
//				item.value.ref = r;
//				table_size_bytes += 2;
//				break;
//			case STRING: // String reference: an uint16 within the constant pool to a UTF-8 string
//				fread(&r.klazz_idx, sizeof(r.klazz_idx), 1, klazz_file.file);
//				r.klazz_idx = be16toh(r.klazz_idx);
//				item.value.ref = r;
//				table_size_bytes += 2;
//				break;
//			case FIELD: // Field reference: two uint16 within the pool, 1st pointing to a Class reference, 2nd to a Name and Type descriptor
//				/* FALL THROUGH TO METHOD */
//			case METHOD: // Method reference: two uint16s within the pool, 1st pointing to a Class reference, 2nd to a Name and Type descriptor
//				/* FALL THROUGH TO INTERFACE_METHOD */
//			case INTERFACE_METHOD: // Interface method reference: 2 uint16 within the pool, 1st pointing to a Class reference, 2nd to a Name and Type descriptor
//				fread(&r.klazz_idx, sizeof(r.klazz_idx), 1, klazz_file.file);
//				fread(&r.name_idx, sizeof(r.name_idx), 1, klazz_file.file);
//				r.klazz_idx = be16toh(r.klazz_idx);
//				r.name_idx = be16toh(r.name_idx);
//				item.value.ref = r;
//				table_size_bytes += 4;
//				break;
//			case NAME: // Name and type descriptor: 2 uint16 to UTF-8 strings, 1st representing a name (identifier), 2nd a specially encoded type descriptor
//				fread(&r.klazz_idx, sizeof(r.klazz_idx), 1, klazz_file.file);
//				fread(&r.name_idx, sizeof(r.name_idx), 1, klazz_file.file);
//				r.klazz_idx = be16toh(r.klazz_idx);
//				r.name_idx = be16toh(r.name_idx);
//				item.value.ref = r;
//				table_size_bytes += 4;
//				break;
//			default:
//				fprintf(stderr, "Found tag byte '%d' but don't know what to do with it\n", tag_byte);
//				item = NULL;
//				break;
//		}
//		if (item != NULL) klazz.items[i-1] = *item;
//	}
//	klazz.pool_size_bytes = table_size_bytes;
//}
    
}
