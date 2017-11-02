# swen755-assignment4

For a test showing how our pooling and scheduling works,go to file pooling.SchedulingTest. The explanation is commented within the file.
<br>
To run the heavy computation, run systemmgmt.Boot. 

We implemented the sum of the log of the elements in a large array using a thread pool of maximum size 10. 
We split the sums into 20 tasks, and sum the partial results at the end in the main thread.
