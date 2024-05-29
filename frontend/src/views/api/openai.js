import axios from "axios";

const openaiApiKey = import.meta.env.VITE_OPENAI_API_KEY;

export async function fetchGPT35Response(prompt) {
  const response = await axios.post(
    "https://api.openai.com/v1/chat/completions",
    {
      model: "gpt-3.5-turbo",
      messages: [{ role: "user", content: prompt }],
    },
    {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${openaiApiKey}`,
      },
    }
  );

  return response.data.choices[0].message.content;
}
