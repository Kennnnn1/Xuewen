
# Read the answers from the .txt file nasa same folder lang din
def open(param, param1, encoding):
    pass


try:
    with open('answers.txt', 'r', encoding='utf-8') as f:
        answers = f.read().splitlines()
except FileNotFoundError:
    answers = ["Sorry, I don't have any answers yet."]
    print("Warning: 'answers.txt' not found. Using default response.")

# Initialize the vectorizer para mag fit yung answers
vectorizer = TfidfVectorizer()
answer_vectors = vectorizer.fit_transform(answers)

def get_response(user_input):
    user_vector = vectorizer.transform([user_input])
    similarities = cosine_similarity(user_vector, answer_vectors)
    max_similarity = similarities.max()
    if max_similarity > 0.1:  # Threshold can be adjusted
        index = similarities.argmax()
        response = answers[index]
    else:
        response = "Sorry I cannot answer that."
    return response

class ChatbotGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("Chatbot")
        self.root.geometry('300x400')  # Adjust mo lang size neto
        self.root.resizable(False, False)

        # Configure grid layout
        self.root.columnconfigure(0, weight=1)
        self.root.rowconfigure(0, weight=1)
        self.root.rowconfigure(1, weight=0)

        # Create a frame for the conversation
        self.frame = ttk.Frame(root, padding="10")
        self.frame.grid(row=0, column=0, sticky="NSEW")

        # Create a Text widget to display the conversation
        self.conversation = tk.Text(self.frame, wrap='word', state='disabled', height=25, width=50, bg="#f0f0f0")
        self.conversation.pack(side='left', expand=True, fill='both')

        # Add scrollbar to the conversation
        self.scrollbar = ttk.Scrollbar(self.frame, command=self.conversation.yview)
        self.conversation['yscrollcommand'] = self.scrollbar.set
        self.scrollbar.pack(side='right', fill='y')

        # Create a frame for the input and send button
        self.input_frame = ttk.Frame(root, padding="10")
        self.input_frame.grid(row=1, column=0, sticky="EW")

        self.input_frame.columnconfigure(0, weight=1)
        self.input_frame.columnconfigure(1, weight=0)

        # Create the input field
        self.input_field = ttk.Entry(self.input_frame, font=("Arial", 12))
        self.input_field.grid(row=0, column=0, padx=(0, 5), pady=5, sticky="EW")
        self.input_field.bind("<Return>", self.send_message)  # Allow sending message with Enter key

        # Create the send button
        self.send_button = ttk.Button(self.input_frame, text="Send", command=self.send_message)
        self.send_button.grid(row=0, column=1, pady=5)

        # Initialize variables for typing animation
        self.typing = False
        self.animation_running = False
        self.animation_dots = 0
        self.typing_text = "Bot is typing"

    def send_message(self, event=None):
        user_input = self.input_field.get().strip()
        if user_input:  # Check if input is not empty
            # Append user input to the conversation area
            self.append_message("You", user_input)
            self.input_field.delete(0, tk.END)  # Clear the input field

            # Start typing animation
            self.start_typing_animation()

            # Start a thread to handle the delayed response
            threading.Thread(target=self.delayed_response, args=(user_input,)).start()

    def append_message(self, sender, message):
        self.conversation.configure(state='normal')
        self.conversation.insert(tk.END, f"{sender}: {message}\n")
        self.conversation.configure(state='disabled')
        self.conversation.see(tk.END)

    def start_typing_animation(self):
        if not self.typing:
            self.typing = True
            self.animation_dots = 0
            self.append_message("Bot", self.typing_text + ".")
            self.animate_typing()

    def animate_typing(self):
        if self.typing:
            self.animation_dots = (self.animation_dots + 1) % 4  # Cycle through 0-3
            dots = '.' * self.animation_dots
            # Remove the last line
            self.conversation.configure(state='normal')
            self.conversation.delete("end-2l", "end-1l")
            self.conversation.insert(tk.END, f"Bot: {self.typing_text}{dots}\n")
            self.conversation.configure(state='disabled')
            self.conversation.see(tk.END)
            # Schedule next animation frame
            self.root.after(500, self.animate_typing)  # Update every 500 ms

    def stop_typing_animation(self):
        self.typing = False
        # Remove typing animation line
        self.conversation.configure(state='normal')
        self.conversation.delete("end-2l", "end-1l")
        self.conversation.configure(state='disabled')

    def delayed_response(self, user_input):
        # Wait for 2 seconds to simulate thinking
        time.sleep(2)
        response = get_response(user_input)

        # Stop typing animation in the main thread
        self.root.after(0, self.stop_typing_animation)

        # Append the bot's response in the main thread
        self.root.after(0, lambda: self.append_message("Bot", response))

def main():
    root = tk.Tk()
    app = ChatbotGUI(root)
    root.mainloop()

if __name__ == "__main__":
    main()
