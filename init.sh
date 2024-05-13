sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie

#curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
#sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl

#curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "../awscliv2.zip"
#unzip ../awscliv2.zip -d ../
#sudo .././aws/install

#curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
#sudo mv /tmp/eksctl /usr/local/bin

#curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.38.0/install.sh | bash
#. ~/.nvm/nvm.sh
#nvm install 14.19.0 && nvm use 14.19.0
#export NODE_OPTIONS=--openssl-legacy-provider

mkdir temp && cd temp &&
wget https://download.joedog.org/siege/siege-4.0.4.tar.gz &&
tar -xvf siege-4.0.4.tar.gz &&
cd siege-4.0.4 &&
./configure --prefix=$HOME/siege > /dev/null 2>&1 &&
sudo make install > /dev/null 2>&1 &&
echo 'export PATH=$PATH:$HOME/siege/bin' >> ~/.bashrc &&
cd /workspace/egov-industrialaccident-unitTest &&

cd infra
docker-compose up
