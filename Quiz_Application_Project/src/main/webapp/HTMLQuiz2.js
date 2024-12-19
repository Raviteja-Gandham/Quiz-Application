function confirmLogout(event) {
  
    // Show the confirmation dialog
    if (confirm("Are you sure you want to quit the exam & Logout?")) {
        // Show the success message
        alert("You have successfully logged out.");
        // Optionally redirect to the logout page
        window.location.href = "LoginForm.html";
    } else {
        // Prevent the default action if the user cancels
        event.preventDefault();
    }
    }
    
    function confirmHome(event) {
      
    // Show the confirmation dialog
    if (confirm("Are you sure you want to quit the exam & go to Home?")) {
        // Show the success message
        alert("You have successfully redirecting to home.");
        // Optionally redirect to the logout page
        window.location.href = "HomePage.html";
    } else {
        // Prevent the default action if the user cancels
        event.preventDefault();
    }
    }

    function confirmSubmit(event) {
      
        // Show the confirmation dialog
        if (confirm("Are you sure you want to submit")) {
            // Show the success message
            alert("You have successfully submitted.");
            // Optionally redirect to the logout page
            window.location.href = "Greet.html";
        } else {
            // Prevent the default action if the user cancels
            event.preventDefault();
        }
        }

		document.addEventListener("DOMContentLoaded", () => {
		    const questionContainers = document.querySelectorAll(".question-container");
		    let score = 0;  // Initialize score

		    questionContainers.forEach(container => {
		        container.addEventListener("click", event => {
		            if (event.target.tagName === "INPUT" && event.target.type === "radio") {
		                const selectedOption = event.target;
		                const isCorrect = selectedOption.getAttribute("data-correct") === "true";
		                
		                // Remove any existing result display
		                const existingResult = container.querySelector(".result");
		                if (existingResult) {
		                    existingResult.remove();
		                }
		                
		                // Display result
		                const resultMessage = document.createElement("p");
		                resultMessage.classList.add("result");
		                resultMessage.textContent = isCorrect ? "Awesome!☺️" : "Sorry!😑";
		                resultMessage.style.color = isCorrect ? "green" : "red";
		                container.appendChild(resultMessage);

		                // Update score if correct answer is selected
		                if (isCorrect) {
		                    score++;
		                }
		                
		                // Disable all options within this question container
		                const radioButtons = container.querySelectorAll("input[type='radio']");
		                radioButtons.forEach(radio => {
		                    radio.disabled = true;
		                });
		            }
		        });
		    });

			
		    // Calculate results on form submission
		    function calculateResults() {
		        // Use the score updated by selecting answers
		        document.getElementById("marks_Obtained").value = score; // Set the score to a hidden field for backend

		        // Display the score dynamically
		        alert(`Quiz Completed! Your score is: ${score}`);

		        return true;  // Allow form submission
		    }
			
	/* HTML Quiz*/
		    // Attach calculateResults to the form submission event
		    const quizForm = document.getElementById("quizForm");
		    if (quizForm) {
		        quizForm.addEventListener("submit", (event) => {
		            event.preventDefault();  // Prevent default form submission to allow score calculation
		            calculateResults();  // Calculate and display score
		            quizForm.submit();  // Submit form after calculation
		        });
		    }
			
			/* CSS Quiz */
			// Attach calculateResults to the form submission event
			const CSSquizForm = document.getElementById("CSSquizForm");
			if (CSSquizForm) {
				CSSquizForm.addEventListener("submit", (event) => {
					event.preventDefault();  // Prevent default form submission to allow score calculation
			  		calculateResults();  // Calculate and display score
			   		CSSquizForm.submit();  // Submit form after calculation
				});
			}

			/* JS Quiz*/
			// Attach calculateResults to the form submission event
						const JSquizForm = document.getElementById("JSquizForm");
						if (JSquizForm) {
							JSquizForm.addEventListener("submit", (event) => {
								event.preventDefault();  // Prevent default form submission to allow score calculation
						  		calculateResults();  // Calculate and display score
						   		JSquizForm.submit();  // Submit form after calculation
							});
						}

		/* SQL Quiz */
		// Attach calculateResults to the form submission event
			const SQLquizForm = document.getElementById("SQLquizForm");
				if (SQLquizForm) {
					SQLquizForm.addEventListener("submit", (event) => {
					event.preventDefault();  // Prevent default form submission to allow score calculation
					calculateResults();  // Calculate and display score
					SQLquizForm.submit();  // Submit form after calculation
			});
	}
	
	/* JAVA Quiz */
			// Attach calculateResults to the form submission event
		const quizFormJava = document.getElementById("quizFormJava");
			if (quizFormJava) {
			quizFormJava.addEventListener("submit", (event) => {
			event.preventDefault();  // Prevent default form submission to allow score calculation
			calculateResults();  // Calculate and display score
			quizFormJava.submit();  // Submit form after calculation
		});
	}
	
	/* Combo Quiz */
				// Attach calculateResults to the form submission event
			const quizFormCombo = document.getElementById("quizFormCombo");
				if (quizFormCombo) {
				quizFormCombo.addEventListener("submit", (event) => {
				event.preventDefault();  // Prevent default form submission to allow score calculation
				calculateResults();  // Calculate and display score
				quizFormCombo.submit();  // Submit form after calculation
			});
		}
		    // Progress Bar
		    const progressBar = document.getElementById("progressBar");
		    const totalQuestions = document.querySelectorAll('[name^="Q"]').length;

		    const updateProgress = () => {
		        const answeredQuestions = document.querySelectorAll('[name^="Q"]:checked').length;
		        const progress = (answeredQuestions / totalQuestions) * 100;

		        progressBar.style.width = progress + "%";
		        progressBar.setAttribute("aria-valuenow", progress);
		        progressBar.innerText = Math.round(progress) + "%";
		    };

		    document.querySelectorAll('[name^="Q"]').forEach((radio) => {
		        radio.addEventListener("change", updateProgress);
		    });
		    
		    // Timer setup
		    let totalTime = 300; // 5 minutes in seconds

		    function startTimer() {
		        const timerElement = document.getElementById("timer");

		        const countdown = setInterval(() => {
		            const minutes = Math.floor(totalTime / 60);
		            const seconds = totalTime % 60;

		            // Display time in MM:SS format
		            timerElement.textContent = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;

		            totalTime--;

		            if (totalTime < 0) {
		                clearInterval(countdown);

		                // Auto-submit the form
		                alert("Time's up! Submitting your quiz.");
		                calculateResults();  // Ensure score is calculated before submitting
		                document.getElementById("quizForm").submit();
		            }
		        }, 1000);
		    }

		    window.onload = startTimer;

		    // Fetch UserId (if required)
		    fetch('GetUserIdServlet')
		        .then(response => {
		            if (!response.ok) {
		                alert("You are not logged in. Redirecting to login page.");
		                window.location.href = "LoginForm.html";
		                return;
		            }
		            return response.json();
		        })
		        .then(data => {
		            document.getElementById("userId").value = data.userId;
		        })
		        .catch(error => console.error("Error fetching userId:", error));		    
		});
