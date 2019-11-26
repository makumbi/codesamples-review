# Rails message controller 
class MessagesController < ApplicationController

  def index
    @messages = current_user.messages
    @message = Message.new
    sleep(3)
  end

  def show
    # Validate input retrieved from parameter 'id'
    validateId(params[:id].first)
    @message = Message.where(:id => params[:id]).first
  end

  def destroy
    # Is the user authorized to delete the message?
    # Implement an authorization control
    isUserAuthorized(current_user)
    # Validates id
    validateId(params[:id].first)
    message = Message.where(:id => params[:id]).first

    if message.destroy
      flash[:success] = "Your message has been deleted."
      # Ensure user_id is validated
      redirect_to user_messages_path(:user_id => current_user.user_id)
    else
      flash[:error] = "Could not delete message."
    end
  end

  def create
    # Implement an authorization control
    isUserAuthorized(current_user)

    if Message.create(message_params)
      respond_to do |format
        # Validates user_id
        validation(current_user.user_id)
        format.html { redirect_to user_messages_path(:user_id => current_user.user_id) }
        format.json { render :json => {:msg => "success"} }
      end
    else
      respond_to do |format|
        # validates users_message_path
        validation(user_messages_path)
        format.html { redirect_to user_messages_path }
        format.json { render :json => {:msg => "failure"} }
      end
    end
  end

  private

  def message_params
    # Validation controls
    validateId(:creator_id)
    validateMessage(:message)
    validateRead(:read)
    validateReceiverId(:receiver_id)
    params.require(:message).permit(:creator_id, :message, :read, :receiver_id)
  end
end
