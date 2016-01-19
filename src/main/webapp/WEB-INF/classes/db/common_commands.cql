/etc/cassandra/

# start JCatascopia:
service JCatascopia-Server start

# list existing keyspaces:
describe keyspaces;

# select keyspace;
use <keyspace name>;

# list existing tables of selected keyspace:
describe tables;