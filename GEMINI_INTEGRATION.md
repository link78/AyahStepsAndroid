# Google Gemini AI Integration

## Overview

This implementation integrates Google's Gemini AI API into the DeenLearn app, providing advanced natural language processing capabilities for Islamic education.

## ✅ Security Update - Key Has Been Revoked

**GOOD NEWS**: The previously exposed API key has been successfully revoked (correct security action!).

### What This Means:

The old key `AIzaSyBLGrGhS2hq3HGtBnmUoYwGzeD53qhtQBY` is **no longer valid** and cannot be used.

### Action Required for Users:

⚠️ **You MUST generate your OWN API key** - Do not try to use the old revoked key.

1. **Get YOUR own new key**:
   - Go to [Google AI Studio](https://makersuite.google.com/app/apikey)
   - Click "Create API Key"
   - Copy YOUR new key

2. **Add to local.properties**:
   - Update `local.properties` with YOUR new key
   - Never share or commit this key
   - Keep it secure and private

## Setup Instructions

### 1. Get a Gemini API Key

1. Go to [Google AI Studio](https://ai.google.dev/)
2. Sign in with your Google account
3. Click "Get API Key"
4. Create a new API key or use an existing one

### 2. Configure API Key Locally

⚠️ **IMPORTANT**: You need to generate YOUR OWN API key. The example key is revoked and will NOT work.

Create or edit `local.properties` file in the project root:

```properties
# Google Gemini API Key (Get your FREE key at https://makersuite.google.com/app/apikey)
gemini.api.key=YOUR_NEW_API_KEY_HERE
```

**Security Reminders**:
- This file is already in `.gitignore` and will NOT be committed to git
- Never share your API key publicly
- Generate your own key from Google AI Studio
- The old example key has been revoked and won't work

### 3. Build Configuration

The API key is automatically loaded from `local.properties` during build:

```kotlin
// app/build.gradle.kts automatically reads the key
buildConfigField("String", "GEMINI_API_KEY", "\"${localProperties.getProperty("gemini.api.key", "")}\"")
```

## Usage

### Basic Usage

```kotlin
val gemini = GeminiService.getInstance()

// Simple chat
val result = gemini.simpleChat("What is Shahada?")
result.onSuccess { response ->
    println("Gemini says: $response")
}

// Multi-turn conversation
var conversationHistory = emptyList<GeminiContent>()
val (response, newHistory) = gemini.continueConversation(
    "Tell me about the 5 pillars of Islam",
    conversationHistory
).getOrNull() ?: return

conversationHistory = newHistory
```

### Integration with AITutorService

You can optionally update `AITutorService` to use Gemini:

```kotlin
class AITutorService {
    private val gemini = GeminiService.getInstance()
    
    suspend fun getResponse(
        question: String,
        topic: Topic,
        isKidsMode: Boolean
    ): Result<ChatMessage> {
        val systemPrompt = buildSystemPrompt(topic, isKidsMode)
        val fullPrompt = "$systemPrompt\n\nQuestion: $question"
        
        return gemini.simpleChat(fullPrompt).map { response ->
            ChatMessage(
                role = "assistant",
                content = response,
                timestamp = System.currentTimeMillis()
            )
        }
    }
    
    private fun buildSystemPrompt(topic: Topic, isKidsMode: Boolean): String {
        val audienceLevel = if (isKidsMode) "children aged 6-12" else "adult learners"
        return """
            You are an Islamic education assistant for the DeenLearn app.
            Provide accurate information about Islam based on Quran and authentic Hadith.
            Adapt your language for $audienceLevel.
            Topic: ${topic.name}
            Always be respectful and encourage learning.
        """.trimIndent()
    }
}
```

### Integration with AIQuestionGeneratorService

```kotlin
class AIQuestionGeneratorService {
    private val gemini = GeminiService.getInstance()
    
    suspend fun generateQuiz(
        category: QuestionCategory,
        count: Int,
        difficulty: QuestionDifficulty,
        isKidsMode: Boolean
    ): Result<Quiz> {
        val prompt = """
            Generate $count multiple choice questions about $category.
            Difficulty: $difficulty
            Target audience: ${if (isKidsMode) "children" else "adults"}
            
            Format each question as JSON:
            {
              "question": "...",
              "options": ["A", "B", "C", "D"],
              "correctAnswer": "A",
              "explanation": "..."
            }
        """.trimIndent()
        
        return gemini.simpleChat(prompt).map { response ->
            parseQuizFromResponse(response)
        }
    }
}
```

## Features

### ✅ Implemented

- Free tier access (60 requests/minute)
- Text generation and completion
- Multi-turn conversations with context
- Safety filters for appropriate content
- Response caching
- Error handling
- Loading states with StateFlow

### 🔄 Available for Implementation

- Image analysis with Gemini Pro Vision (Quran pages, Arabic text)
- Streaming responses
- Custom generation parameters (temperature, topK, topP)
- Advanced safety settings

## API Limits

**Free Tier** (Gemini Pro):
- 60 requests per minute
- 32K token context window
- No cost

**Usage Monitoring**:
- Track requests in Firebase Analytics
- Monitor error rates
- Set up alerts for quota limits

## Best Practices

### Do's ✅

- Store API keys in `local.properties`
- Use `BuildConfig` to access keys
- Implement caching for repeated queries
- Add error handling for all API calls
- Monitor API usage and costs
- Test with real Islamic content
- Verify responses for accuracy

### Don'ts ❌

- Never commit API keys to git
- Don't trust AI responses without verification
- Don't use AI for Islamic rulings (fatwa)
- Don't skip safety settings
- Don't ignore rate limits
- Don't expose keys in logs

## Troubleshooting

### API Key Not Found

```
Error: Gemini API key not configured
```

**Solution**: Add `gemini.api.key` to `local.properties` file.

### Rate Limit Exceeded

```
Error: 429 Too Many Requests
```

**Solution**: Implement exponential backoff or upgrade to paid tier.

### Empty Response

```
Error: No content generated
```

**Solution**: Check prompt format, safety settings, or API status.

## Cost Optimization

### Caching Strategy

```kotlin
// Cache common questions
val cache = mutableMapOf<String, String>()

fun getCachedOrFetch(prompt: String): Result<String> {
    return cache[prompt]?.let { Result.success(it) }
        ?: gemini.simpleChat(prompt).also { result ->
            result.onSuccess { response ->
                cache[prompt] = response
            }
        }
}
```

### Batch Processing

Generate multiple quiz questions at once instead of one at a time.

### Fallback to Simulated AI

Keep simulated AI as fallback when:
- API quota exceeded
- Network unavailable
- Cost limits reached

## Security Checklist

- [x] API key in `local.properties` (gitignored)
- [x] `BuildConfig` for compile-time access
- [x] No hardcoded keys in source code
- [x] Safety settings configured
- [ ] Revoke exposed key
- [ ] Generate new key
- [ ] Update documentation
- [ ] Test with new key

## Resources

- [Google AI for Developers](https://ai.google.dev/)
- [Gemini API Documentation](https://ai.google.dev/docs)
- [Google AI Studio](https://makersuite.google.com/)
- [Safety Settings](https://ai.google.dev/docs/safety_setting_gemini)

## Support

For issues with Gemini integration:
1. Check API key configuration
2. Verify `local.properties` exists
3. Review error logs
4. Check [Google AI Studio](https://ai.google.dev/) for API status

## Next Steps

1. **Immediate**: Revoke the exposed API key
2. **Generate**: Create a new API key
3. **Update**: Replace key in `local.properties`
4. **Test**: Run a simple query
5. **Integrate**: Update AI services to use Gemini
6. **Monitor**: Track usage and performance

---

**Remember**: Keep your API keys secure and never share them publicly!
