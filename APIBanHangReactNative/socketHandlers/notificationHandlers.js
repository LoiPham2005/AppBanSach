const modelNotification = require('../models/model_notification');

const initializeNotificationSocket = (io) => {
  io.on('connection', (socket) => {
    console.log('User connected to notification system:', socket.id);

    // Join user to their personal notification room
    socket.on('join notification room', (userId) => {
      socket.join(`notification_${userId}`);
      console.log('User joined notification room:', userId);
    });

    // Handle new notification
    socket.on('new notification', async (notificationData) => {
      try {
        const { userId, title, message, type, data } = notificationData;
        
        const notification = new modelNotification({
          userId,
          title,
          message,
          type: type || 'system',
          isRead: false,
          data: data || {}
        });

        const savedNotification = await notification.save();
        
        // Emit to specific user's notification room
        io.to(`notification_${userId}`).emit('notification received', savedNotification);
        
      } catch (error) {
        console.error('Error handling notification:', error);
        socket.emit('notification error', { message: 'Error creating notification' });
      }
    });

    socket.on('disconnect', () => {
      console.log('User disconnected from notification system:', socket.id);
    });
  });
};

module.exports = initializeNotificationSocket; 