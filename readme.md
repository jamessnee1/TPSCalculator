Throughput Calculator - Concurrent Users

By James Snee


This Throughput Calculator will calculate the number of 
concurrent users to use in a Test Case scenario/
Thread group for JMeter/LoadRunner, using Little's Law. 
Please note that generally Think Time is for 
the overall scenario and Pacing is per test case step, 
but we use an overall figure for pacing because
you may want to have different pacings for different steps.

This was previously a console-only application, but new features have
recently been added:

- The project is now a Maven-based project
- New GUI interface
- Excel File output

The old console based version is located under src/main/java/Old and
the new GUI version is located under src/main/java.

Any feedback is welcome!

