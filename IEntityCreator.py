# --------------------------
# Fill in these slots
class_name = 'User'
# the name of the table that the entity is stored in
table_name = 'USERLOGINS'
# the type of each attribute and their names
attribute_types_and_names = [('Integer', 'id'),
                             ('String', 'firstName'),
                             ('String', 'lastName'),
                             ('String', 'physicalAddress'),
                             ('String', 'phoneNumber'),
                             ('String', 'emailAddress'),
                             ('Boolean', 'isManufacturer'),
                             ('String', 'password'),
                             ('String', 'brandName')]
# --------------------------

def capitalize_for_hashmap(attribute_name):
    if attribute_name == 'id':
        return attribute_name
    return attribute_name[0].upper() + attribute_name[1:]

# package and imports
code = '''package com.slackers.inc.database.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class {class_name} implements IEntity {{

'''.format(class_name=class_name)


# table name
code += '\tprivate static final String TABLE_NAME = "{0}";\n\n'.format(table_name)


# attributes
for type, name in attribute_types_and_names:
    code += '\tprivate {type} {name};\n'.format(type=type, name=name)


# constructor
code += '''
\tpublic {class_name}(){{

\t}}
'''.format(class_name=class_name)


# getTableName
code += '''
\t\t@Override
\t\tpublic String getTableName() {{
\t\t\treturn "{0}";
\t\t}}'''.format(table_name)


# getEntityValues
code += '''
\t@Override
\tpublic Map<String, Object> getEntityValues() {
\t\tMap<String,Object> out = new HashMap<>();
'''
for _, name in attribute_types_and_names:
    code += '\t\tout.put("{edited_name}", {name});\n'\
        .format(edited_name=capitalize_for_hashmap(name), name=name)

code += '''\t\treturn out;
    }'''


# setEntityValues
code += '''

\t@Override
\tpublic void setEntityValues(Map<String, Object> values) {
'''

for type, name in attribute_types_and_names:
    code += '\t\tif(values.get("{0}") != null){{\n'\
        .format(capitalize_for_hashmap(name))
    code += '\t\t\t{0} = ({1}) values.get("{2}");\n'\
        .format(name, type, capitalize_for_hashmap(name))
    code += '\t\t}\n'

code += '\t}'


# getEntityNameTypePairs
code += '''

\t@Override
\tpublic Map<String, Class> getEntityNameTypePairs() {
\t\tMap<String,Class> out = new HashMap<>();
'''

for type, name in attribute_types_and_names:
    code += '\t\tout.put("{0}", {1}.class);\n'\
        .format(capitalize_for_hashmap(name), type)

code += '''\t\treturn out;
\t}\n\n'''

code += '''\t@Override
\tpublic List<String> tableColumnCreationSettings() {
\t\treturn null; // TODO: this method
\t}
'''

code += '''
\t@Override
\tpublic int getId(){
\t\treturn id;
\t}

}
'''

print(code)