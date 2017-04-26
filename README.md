# Fragen-um-die-Wette
Fragen um die Wette - Wer von euch weiß mehr?

# How to run

 * Start up docker images
   * in the ``docker``-directory run ``docker-compose up -d``
* Start the rest Server
   * in the ``core``-directory run ``mvn exec:java``

# Troubleshooting
## Startup
On the startup the databases are created. This results in some race conditions. If you encounter database related errors, try the following:
 * Delete both cassandra containers
 * Delete both rest containers
 * Start both cassandra images with option ``--no-recreate``
 * Start one rest image with option ``--no-recreate``
 * Wait until initialization is finished
 * Start the other rest instances

Note that this will result in a complete data loss.

## Restarting the Rest Containers
The rest containers use a very simple startup command that clones a git repo. This prevents the container from beeing restarted normally. To restart a rest container, simply delete and create it again. 

## Connections problems
For Windows based hostsystems the host is usually ``192.168.99.100`` (default) while on Linux it is ``localhost``. To change from one system to another change the ``webAddress`` in the ``config.js`` file. If the project was already built, you have to rebuild the apache containers. To do so, stop them, build them and start them with the ``--no-recreate`` option.
