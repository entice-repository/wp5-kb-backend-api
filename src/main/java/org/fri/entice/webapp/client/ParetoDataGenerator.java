package org.fri.entice.webapp.client;

/*
1.  First we should use “queryTime” to show when the query for deployment was sent. Then we need “deploymentTime”.
    The difference between queryTime and deploymentTime will give use the information how much time was needed to
    transfer the image. For now we do not need “endDeploymentTime”, you can keep it null for now.
2.  The size of the fragments could be between 200 and 500 Mbytes, so just generate values between 200 and 500.

3.  For the difference between query time and deployment time I would suggest the following:
        • 20 % of the cases the fragments to be transferred with a theoretical speed od 10 Gbit/s. In reality that is
        like between 812 and 937 Mbyte/s. So if the fragment size is 500 Mbyte, for 937 Mbytes/s the delivery time will
        be 0.5336second.

        • 40 % of the cases the fragments to be transferred through an overloaded 10gibit network with a speed of 437 to
         562 Mbytes. So for 500 Mbyte fragment the minimal transfer time would be 0.8896 seconds.

        • 40% of the cases the fragments to be transferred through 1gibit network with a speed of 87 to 100 Mbyts per
        second. So for 500 Mbyte fragment the minimal transfer time would 5 seconds.

4.  The cost for storing (Costperformance level 1) should be based on amazon pricing at should be between 0.0275 to
    0.0408 usd per GB.

For the scenario I would suggest to have 6 repositories with 10 target clouds and 50 fragments.

The fragments should have been deployed at least 500 times, out of wich 20% on super fast 10Gbit Ethernet,
40% on standard overaloaded 10Gbit Ethernet and 40% on 1Gibit Ethernet.
 */

public class ParetoDataGenerator {
}
