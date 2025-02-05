const modelChat = require('../models/model_chat');

const initializeSocket = (io) => {
  io.on('connection', (socket) => {
    console.log('User connected:', socket.id);

    socket.on('join chat', (chatId) => {
      socket.join(chatId);
      console.log('User joined chat:', chatId);
    });

    socket.on('send message', async (messageData) => {
      try {
        console.log('Received message data:', messageData);
        const { chatId, senderId, content } = messageData;
        
        const chat = await modelChat.findById(chatId)
          .populate('participants', 'username avatar');
        
        if (chat) {
          const newMessage = {
            sender: senderId,
            content,
            timestamp: new Date(),
            isRead: false
          };

          chat.messages.push(newMessage);
          chat.lastMessage = newMessage;
          chat.updatedAt = new Date();
          await chat.save();

          // Broadcast to all users in the chat room
          io.to(chatId).emit('new message', {
            chatId,
            message: {
              ...newMessage,
              sender: { _id: senderId }
            }
          });

          console.log('Message broadcast to room:', chatId);
        }
      } catch (error) {
        console.error('Error handling message:', error);
      }
    });

    socket.on('typing', ({ chatId, userId }) => {
      socket.to(chatId).emit('user typing', { userId });
    });

    socket.on('stop typing', ({ chatId }) => {
      socket.to(chatId).emit('user stop typing');
    });

    socket.on('disconnect', () => {
      console.log('User disconnected:', socket.id);
    });
  });
};

module.exports = initializeSocket;